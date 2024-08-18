package com.demo.daangn.global.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDeailsService customUserDeailsService;
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDeailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
    @Bean
    BCryptPasswordEncoder getBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
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
                .successHandler(new CustomLoginSuccessHandler());
        });
        http.logout((logout) -> {
            logout
                .logoutUrl("/logout").permitAll()
                .invalidateHttpSession(true);
        });

        http.authorizeHttpRequests((authorize) -> {
            authorize
                // main
                .requestMatchers("/img/**", "/js/**", "/css/**", "/api/upload/file/**").permitAll()
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
