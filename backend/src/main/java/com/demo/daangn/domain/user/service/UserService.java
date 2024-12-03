package com.demo.daangn.domain.user.service;

import com.demo.daangn.domain.user.dto.request.DaangnUserModifiedRequest;
import com.demo.daangn.domain.user.dto.request.UserSignUpRequest;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

    // 1. 회원가입 하기
    Integer userSignUp(HttpServletRequest request, UserSignUpRequest signUpRequest);

    // 2. 회원 정보 수정하기(비밀 번호 제외)
    Integer userUpdateDetails(HttpServletRequest request, DaangnUserModifiedRequest userModifiedRequest);

    // 3. 회원 탈퇴하기
    Integer userWithdrawal();

    // 4. 비빌번호 찾기

    // 5. 비밀번호 변경하기

    // 6. 비밀번호 fail count 초기화 하기

    // 7. 프로필 사진 변경하기

    // 8. 프로필 사진 수정하기

    // 9. 회원 정보 조회하기(유저용)

    // 10. 회원 정보 목록 조회하기(유저용)

}
