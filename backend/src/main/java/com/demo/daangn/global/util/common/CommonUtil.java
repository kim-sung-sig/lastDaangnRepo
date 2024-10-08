package com.demo.daangn.global.util.common;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.demo.daangn.domain.user.entity.DaangnUserEntity;
import com.demo.daangn.global.exception.AuthException;
import com.demo.daangn.global.exception.CustomBusinessException;

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

    /**
     * 사용자 로그인 여부 확인
     * @param request
     * @return
     */
    public static Boolean isUserLogin(HttpServletRequest request) {
        return Optional.ofNullable(request.getSession().getAttribute("user"))
                .filter(DaangnUserEntity.class::isInstance)
                .map(DaangnUserEntity.class::cast)
                .isPresent();
    }

    public static void emailValidation(String email) throws CustomBusinessException {
        Pattern emailPattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
        Matcher matcher = emailPattern.matcher(email);

        if (!matcher.matches()) {
            throw new CustomBusinessException("유효하지 않은 이메일 형식입니다.");
        }
    }
}
