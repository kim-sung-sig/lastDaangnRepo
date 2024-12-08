package com.demo.daangn.global.config.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import com.demo.daangn.domain.user.entity.User;
import com.demo.daangn.domain.user.repository.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("로그인 성공" + authentication.getPrincipal());

        CustomUserDatails userDatails = (CustomUserDatails) authentication.getPrincipal();
        User user =  userDatails.getUser();

        userLoginSuccess(user);

        request.getSession().setAttribute("user", user); // 이제 유저를 세션에 올려준것!
		request.getSession().setMaxInactiveInterval(60 * 30); // 세션 시간 30분
        
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_OK);

        // JSON 응답 데이터 생성
        Map<String, Object> data = new HashMap<>();
        data.put("success", true);
        data.put("redirectUrl", getReturnUrl(request, response));
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(data);

        // 응답을 작성
        try (PrintWriter writer = response.getWriter()) {
            writer.write(jsonResponse);
            writer.flush();
        }
    }

    private void userLoginSuccess(User user) {
        user.setPwdFailCount(0);
        user.setLastLoginAt(LocalDateTime.now());
        user.setPwdUnlockCode(null);
        userRepository.save(user);
        log.debug("로그인 성공 후 유저 정보 업데이트 완료");
    }

    private String getReturnUrl(HttpServletRequest request, HttpServletResponse response) {
        RequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        String sessionRedirectUrl = (String) request.getSession().getAttribute("redirectUrl");

        String redirectUrl = (sessionRedirectUrl != null) ? sessionRedirectUrl :
                            (savedRequest != null) ? savedRequest.getRedirectUrl() : "/";
        log.debug("Redirecting to URL: {}", redirectUrl); // 디버깅용 로그 추가
        return redirectUrl;
    }

}
