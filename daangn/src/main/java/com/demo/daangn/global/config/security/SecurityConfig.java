package com.demo.daangn.global.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
                .defaultSuccessUrl("/")
                .successHandler(new CustomLoginSuccessHandler());
        });
        http.logout((logout) -> {
            logout
                .logoutUrl("/logout").permitAll()
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);
        });

        http.authorizeHttpRequests((authorize) -> {
            authorize
                .requestMatchers("/img/**", "/js/**", "/css/**", "/upload/**").permitAll()
                .requestMatchers("/h2-console", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

                .requestMatchers("/", "/join").permitAll()
                // 지역 검색
                .requestMatchers("/region").permitAll()
                // 키워드 검색
                .requestMatchers("/word").permitAll()
                .anyRequest().authenticated();
        });


        return http.build();
    }

}
