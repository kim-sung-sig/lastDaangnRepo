package com.demo.daangn.app.service.user;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

import jakarta.annotation.PostConstruct;
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
    private String saveDir;
    private final String PROFILE_PATH = "profile";
    private Path userProfileLocation;

    /* --- 로그 --- */

    @PostConstruct
    public void init() {
        this.userProfileLocation = Paths.get(saveDir, PROFILE_PATH).toAbsolutePath().normalize();

        // 디버깅용 로그 추가
        log.debug("Initialized user profile location: {}", this.userProfileLocation);

        try {
            Files.createDirectories(this.userProfileLocation);
        } catch (IOException ex) {
            log.error("Could not create the directory where the uploaded files will be stored.", ex);
            throw new IllegalStateException("Failed to initialize file storage directory.", ex);
        }
    }

    // 1. 프로필 사진 등록하기(등록시 이전 사진 업데이트 처리)
    public UserProfileResponse upsertUserProfile(UUID userId, UUID fileId) throws Exception {

        // 로그인한 유저와 프로필 사진의 유저가 일치하는지 확인
        User loginUser = CommonUtil.authCheck(userId);

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
        UserProfile userProfile = new UserProfile(null, loginUser, tempFile);

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
