package com.demo.daangn.global.config.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.demo.daangn.domain.user.entity.DaangnUserEntity;

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
		request.getSession().setAttribute("isLogin", false); // 이제 유저를 세션에 올려준것!
		request.getSession().setMaxInactiveInterval(60*30); // 세션 시간 30분
		response.sendRedirect("/"); // 하고 위치로 보낸다! 만약여기서 원래 보던 곳으로 넘길수 있다면? 좋을듯?
    }

}
