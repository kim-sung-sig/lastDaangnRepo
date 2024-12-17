package com.demo.daangn.app.service.user;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.demo.daangn.app.common.enums.IsUsedEnum;
import com.demo.daangn.app.common.exception.CustomBusinessException;
import com.demo.daangn.app.dao.profile.UserProfileRepository;
import com.demo.daangn.app.dao.temp.file.jpa.TempFileRepository;
import com.demo.daangn.app.dao.user.user.UserRepository;
import com.demo.daangn.app.domain.temp.file.TempFile;
import com.demo.daangn.app.domain.user.User;
import com.demo.daangn.app.domain.user.UserProfile;
import com.demo.daangn.app.service.user.response.UserProfileResponse;
import com.demo.daangn.app.util.CommonUtil;
import com.demo.daangn.app.util.WebpFileUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    /* --- 로그 --- */

    // 1. 프로필 사진 등록하기(등록시 이전 사진 업데이트 처리)
    public UserProfileResponse upsertUserProfile(UUID userId, UUID fileId) throws Exception {

        // String userId2 = "0dab1e38-c9ae-497f-8f4a-888008d7db0b";
        // String fileId2 = "fa5614f0-b3f2-4986-bba6-8b4c30fdba45";

        // userId = UUID.fromString(userId2);
        // fileId = UUID.fromString(fileId2);

        // 로그인한 유저와 프로필 사진의 유저가 일치하는지 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomBusinessException("사용자가 존재하지 않습니다."));

        User loginUser = CommonUtil.getLoginUser()
                .orElseThrow(() -> new CustomBusinessException("로그인이 필요합니다."));

        if(!user.getId().equals(loginUser.getId())) {
            throw new CustomBusinessException("사용자가 일치하지 않습니다.");
        }

        TempFile tempFile = tempFileRepository.findById(fileId)
                .orElseThrow(() -> new CustomBusinessException("파일이 존재하지 않습니다."));

        // 로직 시작
        /*
         * 1. 기존 프로필 사진이 존재하는지 확인
         * 1.1. 존재한다면 is_used를 DISABLED로 변경
         * 2. 새로운 프로필 사진을 등록
         * 2.1 webp 파일로 변환
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
        log.debug("userProfileLocation: {}", userProfileLocation.toAbsolutePath().toString());

        Path savedPath = Paths.get(userProfileLocation.toString(), tempFile.getFileName()).normalize();
        log.debug("savedPath: {}", savedPath.toAbsolutePath().toString());

        UserProfile userProfile = new UserProfile(userProfileId, savedPath.toString(), loginUser, tempFile);
        userProfileRepository.save(userProfile);

        Files.createDirectories(userProfileLocation);
        Files.copy(Paths.get(tempFile.getFilePath()), savedPath, StandardCopyOption.REPLACE_EXISTING);

        // 2.1 webp 파일로 변환
        // TODO 변환 작업이 오래걸릴수 있으므로 백그라운드로 처리하고 조회시 변환된 파일이 없는지 있는지 확인하여야함
        WebpFileUtil.convertToWebp(userProfileLocation, userProfile.getFileName(), 150, 150);

        return null;
    }

    // 2. 프로필 사진 조회하기
    public List<?> getUserProfileList(UUID userId) {
        throw new UnsupportedOperationException("Unimplemented method 'getUserProfileList'");
    }

    // 3. 프로필 사진 상세 조회하기(다운로드목적?)

    // 4. 프로필 사진 다운로드하기

    // 5. 프로필 사진 삭제하기
    public String deleteUserProfile(UUID userId, UUID profileId) {
        // 로그인한 유저와 프로필 사진의 유저가 일치하는지 확인
        CommonUtil.authCheck(userId);

        
        throw new UnsupportedOperationException("Unimplemented method 'deleteUserProfile'");
    }

}
