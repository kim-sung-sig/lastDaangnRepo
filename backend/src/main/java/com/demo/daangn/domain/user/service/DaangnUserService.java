package com.demo.daangn.domain.user.service;

import com.demo.daangn.domain.user.dto.request.DaangnUserModifiedRequest;
import com.demo.daangn.domain.user.dto.request.DaangnUserSignUpRequest;

import jakarta.servlet.http.HttpServletRequest;

public interface DaangnUserService {

    // 1. 회원가입 하기
    Integer userSignUp(HttpServletRequest request, DaangnUserSignUpRequest signUpRequest);

    // 2. 회원 정보 수정하기
    Integer userUpdateDetails(HttpServletRequest request, DaangnUserModifiedRequest userModifiedRequest);

    // 3. 비빌번호 찾기

    // 4. 회원 탈퇴하기
    Integer userWithdrawal(HttpServletRequest request);

}
