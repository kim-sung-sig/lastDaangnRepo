package com.demo.daangn.app.service.user;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(rollbackFor = Exception.class)
    public UserProfileResponse upsertUserProfile(UUID userId, UUID fileId) {
        try {
            // 사용자 확인
            User targetUser = userRepository.findById(userId).orElseThrow(() -> new CustomBusinessException("사용자가 존재하지 않습니다."));
            User loginUser = CommonUtil.getLoginUser().orElseThrow(() -> new CustomBusinessException("로그인이 필요합니다."));

            // 로그인한 유저와 프로필 사진의 유저가 일치하는지 확인
            if(!targetUser.getId().equals(loginUser.getId())) {
                throw new CustomBusinessException("사용자가 일치하지 않습니다.");
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
            Files.createDirectories(userProfileLocation);
            Files.copy(Paths.get(tempFile.getFileFullPath()), Paths.get(userProfileLocation.toString(), tempFile.getFileName()), StandardCopyOption.REPLACE_EXISTING);

            // 2.3 webp 파일로 변환
            // WebpFileUtil.convertToWebp(userProfileLocation, userProfile.getFileName(), 150, 150); // TODO 변환 작업이 오래걸릴수 있으므로 백그라운드로 처리하고 조회시 변환된 파일이 없는지 있는지 확인하여야함
            UserProfileResponse response = UserProfileResponse.of(userProfile);
            return response;

        } catch (CustomBusinessException e) {
            throw e;
        } catch (IOException e) {
            log.error("파일 작업 중 오류 발생", e);
            throw new CustomBusinessException("파일 작업 중 오류가 발생했습니다.");
        } catch (Exception e) {
            log.error("Exception", e);
            throw new CustomBusinessException("예상치 못한 오류가 발생했습니다.");
        }
    }

    // 2. 프로필 사진 조회하기
    public List<UserProfileResponse> getUserProfileList(UUID userId) {
        throw new UnsupportedOperationException("Unimplemented method 'getUserProfileList'");
    }

    // 3. 프로필 사진 상세 조회하기(이미지로드)
    public ResponseEntity<Resource> getUserProfile(UUID userId, UUID profileId, Integer width, Integer height, boolean isDownload) {
        try {
            // 1. 프로필 사진 찾기
            UserProfile userProfile = userProfileRepository.findByUserIdAndId(userId, profileId)
                    .orElseThrow(() -> new CustomBusinessException("프로필 사진이 존재하지 않습니다."));
            
            // 2. 파일 경로 찾기
            // 2.1 사이즈로 파일 경로 찾기
            Path filePathWithFileName = Paths.get(userProfile.getFileFullPath());
            if(!Files.exists(filePathWithFileName)) {
                throw new CustomBusinessException("프로필 사진 파일을 찾을 수 없습니다.");
            }

            // 3. 파일 리소스 로드
            Resource resource = new UrlResource(filePathWithFileName.toUri());
            if (!resource.exists()) {
                throw new CustomBusinessException("프로필 사진 파일을 찾을 수 없습니다.");
            }

            // 4. 응답 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            String encodedFileName = URLEncoder.encode(userProfile.getFileName(), StandardCharsets.UTF_8);
            if (isDownload) {
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"");
            } else {
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + encodedFileName + "\"");
            }
            headers.add(HttpHeaders.CONTENT_TYPE, userProfile.getFileType());

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } catch (CustomBusinessException e) {
            throw e;
        } catch (IOException e) {
            log.error("파일 작업 중 오류 발생", e);
            throw new CustomBusinessException("파일 작업 중 오류가 발생했습니다.");
        } catch (Exception e) {
            log.error("Exception", e);
            throw new CustomBusinessException("프로필 사진을 찾을 수 없습니다.");
        }

    }

    // 4. 프로필 사진 다운로드하기

    // 5. 프로필 사진 삭제하기
    public String deleteUserProfile(UUID userId, UUID profileId) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteUserProfile'");
    }

}
