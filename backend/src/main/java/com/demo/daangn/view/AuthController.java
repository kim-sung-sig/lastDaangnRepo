package com.demo.daangn.view;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.daangn.global.dto.response.RsData;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/api")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<RsData< Boolean >> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        log.info("username: " + username);

        return new ResponseEntity<>(RsData.of(true),  HttpStatus.OK);
    }

    @GetMapping("/status")
    @ResponseBody
    public boolean status(HttpSession session) {
        log.info("로그인 확인함 return {}", session.getAttribute("user") != null);
        return session.getAttribute("user") != null; // 로그인했으면 true 아니면 false
    }
    
}
