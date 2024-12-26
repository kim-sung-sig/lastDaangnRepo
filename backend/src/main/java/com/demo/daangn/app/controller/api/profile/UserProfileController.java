package com.demo.daangn.app.controller.api.profile;

import java.util.List;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.daangn.app.service.user.UserProfileService;
import com.demo.daangn.app.service.user.response.UserProfileResponse;
import com.demo.daangn.app.util.UuidUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserProfileController {

    private final UserProfileService userProfileService;

    /**
     * 사용자 프로필 목록 조회
     * @param userId the user id
     * @return
     */
    @GetMapping("/{userId}/profiles")
    public List<UserProfileResponse> getUserProfileList(@PathVariable("userId") String userId) {
        UUID decryptedUserId = UuidUtil.decrypt(userId);
        List<UserProfileResponse> result = userProfileService.getUserProfileList(decryptedUserId);
        return result;
    }

    /**
     * 사용자 프로필 조회
     * @param userId the user id
     * @param profileId the profile id
     * @return
     */
    @GetMapping("/{userId}/profiles/{profileId}")
    public ResponseEntity<Resource> getUserProfile(@PathVariable("userId") String userId, @PathVariable("profileId") String profileId) {
        UUID decryptedUserId = UuidUtil.decrypt(userId);
        UUID decryptedProfileId = UuidUtil.decrypt(profileId);
        ResponseEntity<Resource> result = userProfileService.getUserProfile(decryptedUserId, decryptedProfileId, null, null, true);
        return result;
    }

    /**
     * 사용자 프로필 사진 등록하기
     * @param userId the user id
     * @param fileId the file id
     * @return
     */
    @PostMapping("/{userId}/profiles")
    public UserProfileResponse upsertUserProfile(@PathVariable("userId") String userId, @RequestBody String fileId) {
        UUID decryptedUserId = UuidUtil.decrypt(userId);
        UUID decryptedFileId = UuidUtil.decrypt(fileId);
        UserProfileResponse result = userProfileService.upsertUserProfile(decryptedUserId, decryptedFileId);
        return result;
    }

    /**
     * 사용자 프로필 사진 활성화
     * @param userId the user id
     * @param profileId the profile id
     * @return
     */
    @PutMapping("/{userId}/profiles/{profileId}/enable")
    public UserProfileResponse enableUserProfile(@PathVariable("userId") String userId, @PathVariable("profileId") String profileId) {
        UUID decryptedUserId = UuidUtil.decrypt(userId);
        UUID decryptedProfileId = UuidUtil.decrypt(profileId);
        UserProfileResponse result = userProfileService.enableUserProfile(decryptedUserId, decryptedProfileId);
        return result;
    }

    /**
     * 사용자 프로필 사진 비활성화
     * @param userId the user id
     * @param profileId the profile id
     */
    @DeleteMapping("/{userId}/profiles/{profileId}/disable")
    public void disableUserProfile(@PathVariable("userId") String userId, @PathVariable("profileId") String profileId) {
        UUID decryptedUserId = UuidUtil.decrypt(userId);
        UUID decryptedProfileId = UuidUtil.decrypt(profileId);
        userProfileService.disableUserProfile(decryptedUserId, decryptedProfileId);
    }

    /**
     * 사용자 프로필 사진 삭제
     * @param userId
     * @param profileId
     */
    @DeleteMapping("/{userId}/profiles/{profileId}")
    public void deleteUserProfile(@PathVariable("userId") String userId, @PathVariable("profileId") String profileId) {
        UUID decryptedUserId = UuidUtil.decrypt(userId);
        UUID decryptedProfileId = UuidUtil.decrypt(profileId);
        userProfileService.deleteUserProfile(decryptedUserId, decryptedProfileId);
    }

}
