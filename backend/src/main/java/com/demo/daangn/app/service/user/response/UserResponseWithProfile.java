package com.demo.daangn.app.service.user.response;

import lombok.Data;

@Data
public class UserResponseWithProfile {

    private UserResponse user;

    private UserProfileResponse userProfile;

    public UserResponseWithProfile(UserResponse user, UserProfileResponse userProfile) {
        this.user = user;
        this.userProfile = userProfile;
    }

}
