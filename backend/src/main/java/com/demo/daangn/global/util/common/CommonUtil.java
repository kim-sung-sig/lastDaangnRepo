package com.demo.daangn.global.util.common;

import java.util.Collection;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.demo.daangn.domain.user.entity.User;
import com.demo.daangn.global.config.security.CustomUserDatails;
import com.demo.daangn.global.exception.CustomBusinessException;

/*
 * 자주 사용될 공통 메소드를 정의하는 클래스
 */
public class CommonUtil {

    /**
     * 현재 로그인한 유저 조회
     * @return
     */
    public static Optional<User> getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof CustomUserDatails) {
            CustomUserDatails userDatails = (CustomUserDatails) authentication.getPrincipal();
            User loginUser = userDatails.getUser();
            return Optional.of(loginUser);
        }
        return Optional.empty();
    }

    public static Optional<UUID> getLoginUserUuid() {
        return getLoginUser()
                .map(User::getId);
    }

    public static boolean isNotBlank(String... strs) {
        for (String str : strs) {
            if (str == null || str.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return str != null && !str.isEmpty();
    }

    public static boolean isNotBlank(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }

    public static boolean isNotBlank(Object obj) {
        if (obj == null) return false;
        if (obj instanceof String) return isNotBlank((String) obj);
        if (obj instanceof Collection) return isNotBlank((Collection<?>) obj);
        return true;
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
