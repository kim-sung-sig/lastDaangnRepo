package com.demo.daangn.global.config.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.demo.daangn.domain.user.entity.DaangnUserEntity;
import com.demo.daangn.global.dto.response.RsData;

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
        DaangnUserEntity user =  userDatails.getUserEntity();
        
        request.getSession().setAttribute("user", user); // 이제 유저를 세션에 올려준것!
		request.getSession().setMaxInactiveInterval(60*30); // 세션 시간 30분
		// response.sendRedirect("/");
        
        ResponseEntity<RsData<Boolean>> responseEntity = new ResponseEntity<>(RsData.of(true), HttpStatus.OK);
        response.encodeURL(getDefaultTargetUrl());
        response.setContentType("application/json");
        response.getWriter().write(responseEntity.getBody().toString());
		// response.sendRedirect("http://localhost:3000"); // 뷰로 할시
		response.sendRedirect("/"); // 스프링시
    }

}
