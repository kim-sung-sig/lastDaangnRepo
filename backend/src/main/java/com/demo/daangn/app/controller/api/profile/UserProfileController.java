package com.demo.daangn.app.controller.api.profile;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.daangn.app.service.user.UserProfileService;
import com.demo.daangn.app.service.user.response.UserProfileResponse;
import com.demo.daangn.app.util.CommonUtil;
import com.demo.daangn.app.util.UuidUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping("/{userId}/profiles")
    public List<?> getUserProfileList(@PathVariable("userId") String userId) {
        userProfileService.getUserProfileList(UuidUtil.decrypt(userId));
        return null;
    }

    @GetMapping("/{userId}/profiles/{profileId}")
    public ResponseEntity<Resource> getUserProfileList(@PathVariable("userId") String userId, @PathVariable("profileId") String profileId) {
        return userProfileService.getUserProfile(UuidUtil.decrypt(userId), UuidUtil.decrypt(profileId), null, null, false);
    }

    @PostMapping("/{userId}/profiles")
    public UserProfileResponse upsertUserProfile(@PathVariable("userId") String userId, @RequestBody String fileId) {
        return userProfileService.upsertUserProfile(UuidUtil.decrypt(userId), UuidUtil.decrypt(fileId));
    }
    

    @GetMapping("/profiles")
    public List<?> getUserProfileList() {
        userProfileService.getUserProfileList(CommonUtil.getLoginUserId()
                .orElseThrow(() -> new IllegalArgumentException("로그인한 유저가 없습니다.")));
        return null;
    }

}
