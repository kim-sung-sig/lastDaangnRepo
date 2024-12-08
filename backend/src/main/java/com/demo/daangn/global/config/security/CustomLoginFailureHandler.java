package com.demo.daangn.global.config.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.demo.daangn.domain.user.entity.User;
import com.demo.daangn.domain.user.repository.user.UserRepository;
import com.demo.daangn.global.util.common.CommonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String message = exception.getMessage();
        String username = request.getParameter("username");

        if(exception instanceof InternalAuthenticationServiceException) { // 0. db 에러
            message = "INTERNAL_SERVER_ERROR";
        } else if (exception instanceof AccountExpiredException) {      // 1. 계정 만료로 잠김 isAccountNonExpired
            message = "LOCKED";
        } else if (exception instanceof LockedException) {              // 2. 계정 잠김 isAccountNonLocked
            message = "LOCKED";
        } else if (exception instanceof CredentialsExpiredException) {  // 3. 비밀번호 변경 필요 isCredentialsNonExpired (이건 나중에 판별)
            message = "LOCKED";
        } else if (exception instanceof DisabledException) {            // 4. 계정 사용 불가 isAccountNonExpired
            message = "DISABLED";
        } else if (exception instanceof UsernameNotFoundException) {    // 5. UserDetailsService에서 유저를 찾을 수 없음
            message = "USERNAME_NOT_FOUND";
        } else if (exception instanceof BadCredentialsException) {      // 6. 비밀번호 불일치
            message = BadCredentialsExceptionHandle(username);
        }

        // 응답을 작성
        returnMessage(response, message);
    }

    private String BadCredentialsExceptionHandle(String username) {
        String message = "비밀번호가 일치하지 않습니다.";
        Optional<User> userOp = userRepository.findByUsername(username);

        if(!userOp.isPresent()) {
            return "USERNAME_NOT_FOUND";
        }
        User user = userOp.get();
        if(user.getIsUsed() == 0) {
            return "DISABLED";
        }
        if (user.getIsUsed() == 2) {
            return "LOCKED";
        }

        Integer failCount = user.getPwdFailCount() + 1;
        if(failCount >= 15) { // 15회 이상 실패시 계정 잠김
            userLockProcess(user);
            message = "LOCKED";
        } else {
            user.setPwdFailCount(failCount);
            userRepository.save(user);
            message = "PASSWORD_NOT_MATCH:" + failCount;
        }

        return message;
    }

    private void userLockProcess(User user) {
        user.setPwdUnlockCode(CommonUtil.generateRandomKey());
        user.setIsUsed(2);
        userRepository.save(user);
        log.debug("username : {} 계정 잠김 처리", user.getUsername());
    }

    private void returnMessage(HttpServletResponse response, String message) throws IOException {
        Map<String, Object> returnData = new HashMap<>();
        returnData.put("success", false);
        returnData.put("message", message);

        // 응답을 작성
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(returnData);

        try (PrintWriter writer = response.getWriter()) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            writer.write(jsonResponse);
            writer.flush();
        }
    }

}
