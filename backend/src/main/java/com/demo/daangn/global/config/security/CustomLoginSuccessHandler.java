package com.demo.daangn.global.config.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.demo.daangn.domain.user.entity.DaangnUserEntity;

import jakarta.servlet.FilterChain;
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

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        return super.determineTargetUrl(request, response);
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        return super.determineTargetUrl(request, response, authentication);
    }

    @Override
    protected RedirectStrategy getRedirectStrategy() {
        return super.getRedirectStrategy();
    }

    @Override
    protected String getTargetUrlParameter() {
        return super.getTargetUrlParameter();
    }

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        super.handle(request, response, authentication);
    }

    @Override
    protected boolean isAlwaysUseDefaultTargetUrl() {
        return super.isAlwaysUseDefaultTargetUrl();
    }

    @Override
    public void setAlwaysUseDefaultTargetUrl(boolean alwaysUseDefaultTargetUrl) {
        super.setAlwaysUseDefaultTargetUrl(alwaysUseDefaultTargetUrl);
    }

    @Override
    public void setDefaultTargetUrl(String defaultTargetUrl) {
        super.setDefaultTargetUrl(defaultTargetUrl);
    }

    @Override
    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        super.setRedirectStrategy(redirectStrategy);
    }

    @Override
    public void setTargetUrlParameter(String targetUrlParameter) {
        super.setTargetUrlParameter(targetUrlParameter);
    }

    @Override
    public void setUseReferer(boolean useReferer) {
        super.setUseReferer(useReferer);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        super.onAuthenticationSuccess(request, response, chain, authentication);
    }
    
}
