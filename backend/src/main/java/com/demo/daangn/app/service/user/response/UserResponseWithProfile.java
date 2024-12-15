package com.demo.daangn.app.service.user.response;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserResponseWithProfile extends UserResponse {

    private UserProfileResponse userProfile;

}
