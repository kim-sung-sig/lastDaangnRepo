package com.demo.daangn.app.service.user;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.demo.daangn.app.dao.profile.UserProfileRepository;
import com.demo.daangn.app.dao.user.user.UserRepository;
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

    /* --- 상수 및 변수 --- */

    /* --- 로그 --- */

    public String updateUserProfile(String userUuid, String fileId) {
        throw new UnsupportedOperationException("Unimplemented method 'updateUserProfile'");
    }

    // 4.1.1 프로필 사진 변경하기(my)
    public String updateUserProfile(String fileId) {
        String userUuid = CommonUtil.getLoginUserId().toString();
        return updateUserProfile(userUuid, fileId);
    }

    // 4.2 프로필 사진 조회하기
    public List<?> getUserProfileList(UUID userId) {
        throw new UnsupportedOperationException("Unimplemented method 'getUserProfileList'");
    }

    // 4.4 프로필 사진 상세 조회하기(다운로드목적?)

    // 4.5 프로필 사진 다운로드하기

    // 4.6 프로필 사진 삭제하기
    public String deleteUserProfile() {
        throw new UnsupportedOperationException("Unimplemented method 'deleteUserProfile'");
    }

    // 4.6.1 프로필 사진 삭제하기(my)

}
