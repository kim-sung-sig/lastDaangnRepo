package com.demo.daangn.global.util.common;

import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.demo.daangn.domain.user.entity.User;
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
    public static User getUser(HttpServletRequest request) throws AuthException {
        return Optional.ofNullable(request.getSession().getAttribute("user"))
                .filter(User.class::isInstance)
                .map(User.class::cast)
                .orElseThrow(() -> new AuthException("사용자 정보가 없습니다."));
    }

    /**
     * 사용자 로그인 여부 확인
     * @param request
     * @return
     */
    public static Boolean isUserLogin(HttpServletRequest request) {
        return Optional.ofNullable(request.getSession().getAttribute("user"))
                .filter(User.class::isInstance)
                .map(User.class::cast)
                .isPresent();
    }

    /**
     * 이메일 유효성 검사
     * @param email
     * @return
     * @throws CustomBusinessException
     */
    public static boolean emailValidation(String email) throws CustomBusinessException {
        if(email == null || email.isEmpty()) {
            return false;
        }
        Pattern emailPattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
        Matcher matcher = emailPattern.matcher(email);

        boolean result = matcher.matches();
        return result;
    }

    /**
     * 전화번호 유효성 검사
     * @param phone
     * @return
     * @throws CustomBusinessException
     */
    public static boolean phoneValidation(String phone) throws CustomBusinessException {
        if(phone == null || phone.isEmpty()) {
            return false;
        }
        Pattern phonePattern = Pattern.compile("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$");
        Matcher matcher = phonePattern.matcher(phone);

        boolean result = matcher.matches();
        return result;
    }

    public static String generateRandomKey() {
        Random random = new Random();
        String randomNumber = IntStream.range(0, 6)
                .map(i -> random.nextInt(10))
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
        return randomNumber;
    }

}
