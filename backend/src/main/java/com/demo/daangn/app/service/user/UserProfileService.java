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
        User loginUser = CommonUtil.getLoginUser()
                .orElseThrow(() -> new AuthException("로그인이 필요합니다."));

        // 로그인한 유저와 프로필 사진의 유저가 일치하는지 확인
        if(!targetUser.getId().equals(loginUser.getId())) {
            throw new AuthException("사용자가 일치하지 않습니다.");
        }

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
        userProfileRepository.findByUserIdAndIsUsed(userId, IsUsedEnum.ENABLED).ifPresent(userProfile -> {
            // 1.1. 존재한다면 is_used를 DISABLED로 변경
            userProfile.DISABLED();
            userProfileRepository.save(userProfile);
        });

        // 2. 새로운 프로필 사진을 등록
        UUID userProfileId = UUID.randomUUID();
        Path userProfileLocation = Paths.get(SAVE_DIR + "user", userId.toString(), PROFILE_PATH, userProfileId.toString());
        log.debug("userProfileLocation: {}", userProfileLocation.toString());

        // 2.1. DB에 저장
        UserProfile userProfile = new UserProfile(userProfileId, userProfileLocation, loginUser, tempFile);
        userProfileRepository.save(userProfile);

        // 2.2. 파일 이동
        CustomFileUtil.copy(Paths.get(tempFile.getFileFullPath()), userProfileLocation.resolve(tempFile.getFileName()));

        // 2.3 webp 파일로 변환
        // WebpFileUtil.convertToWebp(userProfileLocation, userProfile.getFileName(), 150, 150); // TODO 변환 작업이 오래걸릴수 있으므로 백그라운드로 처리하고 조회시 변환된 파일이 없는지 있는지 확인하여야함
        UserProfileResponse response = UserProfileResponse.of(userProfile);
        return response;
    }

    /**
     * 사용자 프로필 사진 목록 조회
     * @param userId 사용자 ID
     * @return
     */
    public List<UserProfileResponse> getUserProfileList(UUID userId) {
        List<UserProfile> profileList = userProfileRepository.findByUserIdAndIsUsedNotOrderByIsUsed(userId, IsUsedEnum.DISABLED);
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
        // 1. 프로필 사진 찾기
        UserProfile userProfile = userProfileRepository.findByUserIdAndId(userId, profileId)
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

    /**
     * 프로필 사진 삭제하기
     * @param userId 사용자 ID
     * @param profileId 프로필 사진 ID
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserProfile(UUID userId, UUID profileId) {

        // 1. 프로필 사진 찾기
        UserProfile userProfile = userProfileRepository.findByUserIdAndId(userId, profileId)
                .orElseThrow(() -> new CustomBusinessException("프로필 사진이 존재하지 않습니다."));

        // 2. 권한 확인
        User loginUser = CommonUtil.authCheck(userId);

        // 3. 일치 여부 확인
        if(!userProfile.getUser().getId().equals(loginUser.getId())) {
            throw new AuthException("사용자가 일치하지 않습니다.");
        }

        // 4. 파일 삭제
        userProfile.DELETED();
        userProfileRepository.save(userProfile);
    }

    /**
     * 프로필 사진 활성화하기
     * @param userId 사용자 ID
     * @param profileId 프로필 사진 ID
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public UserProfileResponse enableUserProfile(UUID userId, UUID profileId) {
        
        // 1. 프로필 사진 찾기
        UserProfile selectedUserProfile = userProfileRepository.findByUserIdAndIdAndIsUsed(userId, profileId, IsUsedEnum.DISABLED)
                .orElseThrow(() -> new CustomBusinessException("프로필 사진이 존재하지 않습니다."));

        // 2. 로그인 유저 확인
        User loginUser = CommonUtil.authCheck(userId);

        // 3. 일치 여부 확인
        if(!selectedUserProfile.getUser().getId().equals(loginUser.getId())) {
            throw new AuthException("사용자가 일치하지 않습니다.");
        }

        // 4. 기존 활성화된 프로필 사진 비활성화
        userProfileRepository.findByUserIdAndIsUsed(userId, IsUsedEnum.ENABLED).ifPresent(userProfile1 -> {
            userProfile1.DISABLED();
            userProfileRepository.save(userProfile1);
        });

        // 5. 선택된 프로필 사진 활성화
        selectedUserProfile.ENABLED();
        userProfileRepository.save(selectedUserProfile);

        return UserProfileResponse.of(selectedUserProfile);
    }

}
