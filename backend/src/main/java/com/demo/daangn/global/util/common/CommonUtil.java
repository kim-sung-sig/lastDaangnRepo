package com.demo.daangn.global.util.common;

import java.util.Optional;

import com.demo.daangn.domain.user.entity.DaangnUserEntity;
import com.demo.daangn.global.exception.AuthException;

import jakarta.servlet.http.HttpServletRequest;

/*
 * 자주 사용될 공통 메소드를 정의하는 클래스
 */
public class CommonUtil {

    /**
     * 사용자 정보 가져오기
     * 세션올 올라간 유저 정보를 찾아온다.
     * @param request
     * @throws AuthException
     * @return
     */
    public static DaangnUserEntity getUser(HttpServletRequest request) throws AuthException {
        return Optional.ofNullable(request.getSession().getAttribute("user"))
                .filter(DaangnUserEntity.class::isInstance)
                .map(DaangnUserEntity.class::cast)
                .orElseThrow(() -> new AuthException("사용자 정보가 없습니다."));
    }

    public static Boolean isUserLogin(HttpServletRequest request) {
        return Optional.ofNullable(request.getSession().getAttribute("user"))
                .filter(DaangnUserEntity.class::isInstance)
                .map(DaangnUserEntity.class::cast)
                .isPresent();
    }
}
