package com.demo.daangn.app.service.user;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.daangn.app.common.enums.IsUsedEnum;
import com.demo.daangn.app.common.exception.AuthException;
import com.demo.daangn.app.common.exception.CustomBusinessException;
import com.demo.daangn.app.dao.profile.UserProfileRepository;
import com.demo.daangn.app.dao.temp.file.jpa.TempFileRepository;
import com.demo.daangn.app.dao.user.user.UserRepository;
import com.demo.daangn.app.domain.temp.file.TempFile;
import com.demo.daangn.app.domain.user.User;
import com.demo.daangn.app.domain.user.UserProfile;
import com.demo.daangn.app.service.user.response.UserProfileResponse;
import com.demo.daangn.app.util.CommonUtil;
import com.demo.daangn.app.util.CustomFileUtil;
import com.drew.lang.annotations.NotNull;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    /* --- 의존성 --- */
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final TempFileRepository tempFileRepository;

    /* --- 상수 및 변수 --- */
    @Value("${custom.fileDirPath}")
    private String SAVE_DIR;
    private final String PROFILE_PATH = "profile";
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 프로필 사진 등록하기
     * @param userId 사용자 ID
     * @param fileId 파일 ID
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public UserProfileResponse upsertUserProfile(UUID userId, UUID fileId) {
        // 사용자 확인
        User targetUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomBusinessException("사용자가 존재하지 않습니다."));
        checkUserPermission(targetUser);

        // 임시 파일 확인
        TempFile tempFile = tempFileRepository.findById(fileId)
                .orElseThrow(() -> new CustomBusinessException("파일이 존재하지 않습니다."));

        // 로직 시작
        /*
            * 1. 기존 프로필 사진이 존재하는지 확인
            *  1.1. 존재한다면 is_used를 DISABLED로 변경
            *
            * 2. 새로운 프로필 사진을 등록
            *  2.1 DB에 저장
            *  2.2 파일 이동
            *  2.3 webp 파일로 변환
            */
        // 1. 기존 프로필 사진이 존재하는지 확인
        userProfileRepository.findByUserAndIsUsed(targetUser, IsUsedEnum.ENABLED).ifPresent(userProfile -> {
            // 1.1. 존재한다면 is_used를 DISABLED로 변경
            userProfile.DISABLED();
            userProfileRepository.save(userProfile);
        });

        // 2. 새로운 프로필 사진을 등록
        UUID userProfileId = UUID.randomUUID();
        Path userProfileLocation = Paths.get(SAVE_DIR + "user", userId.toString(), PROFILE_PATH, userProfileId.toString());
        log.debug("userProfileLocation: {}", userProfileLocation.toString());

        // 2.1. DB에 저장
        UserProfile userProfile = new UserProfile(userProfileId, userProfileLocation, targetUser, tempFile);
        userProfileRepository.save(userProfile);

        // 2.2. 파일 이동
        CustomFileUtil.copy(Paths.get(tempFile.getFileFullPath()), userProfileLocation.resolve(tempFile.getFileName()));

        // webp 변환 (TODO: 비동기 처리 고려)
        // WebpFileUtil.convertToWebp(userProfileLocation, userProfile.getFileName(), 150, 150);

        UserProfileResponse response = UserProfileResponse.of(userProfile);
        return response;
    }

    /**
     * 사용자 프로필 사진 목록 조회
     * @param userId 사용자 ID
     * @return
     */
    public List<UserProfileResponse> getUserProfileList(UUID userId) {
        User targetUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomBusinessException("사용자가 존재하지 않습니다."));

        List<UserProfile> profileList = userProfileRepository.findByUserAndIsUsedNotOrderByIsUsed(targetUser, IsUsedEnum.DISABLED);
        return UserProfileResponse.of(profileList);
    }

    /**
     * 프로필 사진 조회하기
     * @param userId 사용자 ID
     * @param profileId 프로필 사진 ID
     * @param width 요청 사진 가로 길이
     * @param height 요청 사진 세로 길이
     * @param isDownload 다운로드 여부
     * @return
     */
    public ResponseEntity<Resource> getUserProfile(UUID userId, UUID profileId, Integer width, Integer height, boolean isDownload) {
        User targetUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomBusinessException("사용자가 존재하지 않습니다."));

        // 1. 프로필 사진 찾기
        UserProfile userProfile = userProfileRepository.findByUserAndIdAndIsUsedNot(targetUser, profileId, IsUsedEnum.DELETED)
                .orElseThrow(() -> new CustomBusinessException("프로필 사진이 존재하지 않습니다."));

        // 2. 파일 경로 찾기
        Path filePath = Paths.get(userProfile.getFilePath());
        String fileName = userProfile.getFileName();
        Resource resource = CustomFileUtil.getFileResource(filePath, fileName)
                .orElseThrow(() -> new CustomBusinessException("프로필 사진 파일을 찾을 수 없습니다."));

        // 3. 응답 헤더 설정
        String encodedFileName = URLEncoder.encode(userProfile.getFileName(), StandardCharsets.UTF_8);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, userProfile.getFileType());
        headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format(
            isDownload ? "attachment; filename=\"%s\"" : "inline; filename=\"%s\""
            , encodedFileName));

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    public UserProfileResponse getProfileDetail(UUID profileId) {
        UserProfile userProfile = userProfileRepository.findById(profileId)
                .orElseThrow(() -> new CustomBusinessException("프로필 사진이 존재하지 않습니다."));
        return UserProfileResponse.of(userProfile);
    }

    /**
     * 프로필 사진 활성화하기
     * @param userId 사용자 ID
     * @param profileId 프로필 사진 ID
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public UserProfileResponse enableUserProfile(UUID userId, UUID profileId) {
        User targetUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomBusinessException("사용자가 존재하지 않습니다."));
        checkUserPermission(targetUser);

        // 1. 프로필 사진 찾기
        UserProfile selectedUserProfile = userProfileRepository.findByUserAndIdAndIsUsedNot(targetUser, profileId, IsUsedEnum.DELETED)
                .orElseThrow(() -> new CustomBusinessException("프로필 사진이 존재하지 않습니다."));

        // 2. 기존 활성화된 프로필 사진 비활성화
        userProfileRepository.findByUserAndIsUsed(targetUser, IsUsedEnum.ENABLED).ifPresent(existingProfile -> {
            existingProfile.DISABLED();
            userProfileRepository.save(existingProfile);
        });

        // 3. 선택된 프로필 사진 활성화
        selectedUserProfile.ENABLED();
        userProfileRepository.save(selectedUserProfile);

        return UserProfileResponse.of(selectedUserProfile);
    }

    /**
     * 프로필 사진 비활성화하기
     * @param userId 사용자 ID
     * @param profileId 프로필 사진 ID
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void disableUserProfile(UUID userId, UUID profileId) {
        User targetUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomBusinessException("사용자가 존재하지 않습니다."));
        checkUserPermission(targetUser);

        // 1. 프로필 사진 찾기
        UserProfile userProfile = userProfileRepository.findByUserAndIdAndIsUsedNot(targetUser, profileId, IsUsedEnum.DELETED)
                .orElseThrow(() -> new CustomBusinessException("프로필 사진이 존재하지 않습니다."));

        // 2. 일치 여부 확인
        if(!userProfile.getUser().getId().equals(targetUser.getId())) {
            throw new AuthException("사용자가 일치하지 않습니다.");
        }

        // 3. 프로필 삭제
        userProfile.DISABLED();
        userProfileRepository.save(userProfile);
    }

    /**
     * 프로필 사진 삭제하기
     * @param userId 사용자 ID
     * @param profileId 프로필 사진 ID
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserProfile(UUID userId, UUID profileId) {
        User targetUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomBusinessException("사용자가 존재하지 않습니다."));
        checkUserPermission(targetUser);

        // 1. 프로필 사진 찾기
        UserProfile userProfile = userProfileRepository.findByUserAndIdAndIsUsedNot(targetUser, profileId, IsUsedEnum.DELETED)
                .orElseThrow(() -> new CustomBusinessException("프로필 사진이 존재하지 않습니다."));

        // 2. 일치 여부 확인
        if(!userProfile.getUser().getId().equals(targetUser.getId())) {
            throw new AuthException("사용자가 일치하지 않습니다.");
        }

        // 3. 프로필 삭제
        userProfile.DELETED();
        userProfileRepository.save(userProfile);
    }

    /**
     * 로그인 유저와 타겟 유저가 일치하는지 체크하는 메서드
     * @param userId 사용자 ID
     * @param targetUser 확인할 대상 유저
     */
    private void checkUserPermission(@NotNull User targetUser) {
        User loginUser = CommonUtil.getLoginUser()
                .orElseThrow(() -> new AuthException("로그인이 필요합니다."));
        if (!targetUser.getId().equals(loginUser.getId())) {
            throw new AuthException("사용자가 일치하지 않습니다.");
        }
    }

}
