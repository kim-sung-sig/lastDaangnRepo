package com.demo.daangn.app.controller.api.profile;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.daangn.app.service.user.UserProfileService;
import com.demo.daangn.app.util.CommonUtil;

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
        userProfileService.getUserProfileList(CommonUtil.inputToUUID(userId));
        return null;
    }

    @GetMapping("/profiles")
    public List<?> getUserProfileList() {
        userProfileService.getUserProfileList(CommonUtil.getLoginUserId()
                .orElseThrow(() -> new IllegalArgumentException("로그인한 유저가 없습니다.")));
        return null;
    }

}
