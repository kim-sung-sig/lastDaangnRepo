package com.demo.daangn.app.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.demo.daangn.app.config.security.handler.CustomLoginFailureHandler;
import com.demo.daangn.app.config.security.handler.CustomLoginSuccessHandler;
import com.demo.daangn.app.config.security.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDeailsService;

    @Autowired
    private CustomLoginSuccessHandler customLoginSuccessHandler;

    @Autowired
    private CustomLoginFailureHandler customLoginFailureHandler;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDeailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    BCryptPasswordEncoder getBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable());
        http.httpBasic((basic) -> basic.disable());
        http.oauth2Login((oauth) -> oauth.disable()); // 일단 막자

        http.formLogin((form) -> {
            form
                .loginPage("/login").permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(customLoginSuccessHandler)
                .failureHandler(customLoginFailureHandler);
        });
        http.logout((logout) -> {
            logout
                .logoutUrl("/logout").permitAll()
                .invalidateHttpSession(true);
        });

        http.authorizeHttpRequests((authorize) -> {
            authorize
                .requestMatchers("/components/**", "/css/**", "/js/**", "/img/**").permitAll() // static resources
                // user
                .requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll() // 회원가입
                .requestMatchers(HttpMethod.GET, "/api/v1/users/check/**").permitAll() // 중복확인
                .requestMatchers(HttpMethod.GET, "/api/user/status").permitAll() // 로그인 상태 확인

                // usedBoard
                .requestMatchers("/test1", "/test2", "/test3", "/test2/**").permitAll()

                // swagger
                .requestMatchers("/h2-console", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

                // admin
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();
        });

        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        http.sessionManagement((session) -> {
            session
                .invalidSessionUrl("/login")
                .sessionAuthenticationErrorUrl("/login")
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .sessionFixation().none()
                .maximumSessions(1);
        });
        return http.build();
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://127.0.0.1:3000");
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3000L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    CorsFilter corsFilter() {
        CorsConfigurationSource source = corsConfigurationSource();
        return new CorsFilter(source);
    }

}
