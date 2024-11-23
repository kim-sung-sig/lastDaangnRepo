package com.demo.daangn.global.config.security;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.demo.daangn.domain.user.entity.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("로그인 성공" + authentication.getPrincipal());
        CustomUserDatails userDatails = (CustomUserDatails) authentication.getPrincipal();
        User user =  userDatails.getUser();
        
        request.getSession().setAttribute("user", user); // 이제 유저를 세션에 올려준것!
		request.getSession().setMaxInactiveInterval(60*30); // 세션 시간 30분
        
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);

        // JSON 응답 데이터 생성
        String jsonResponse = "{\"success\":true}";

        // 응답을 작성
        try (PrintWriter writer = response.getWriter()) {
            writer.write(jsonResponse);
            writer.flush();
        }
		// response.sendRedirect("http://localhost:3000"); // 뷰로 할시
		// response.sendRedirect("/"); // 스프링시
    }

}
