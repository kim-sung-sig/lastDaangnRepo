package com.demo.daangn.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.daangn.domain.user.repository.DaangnUserRepository;
import com.demo.daangn.global.dto.response.RsData;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequestMapping("/api")
@RestController
public class AuthController {

    @Autowired
    private DaangnUserRepository userRepository;

    @GetMapping("/status")
    public ResponseEntity<RsData< Boolean >> status(HttpSession session) {
        Boolean result = session.getAttribute("user") != null;
        log.info("로그인 확인함 return {}", result);
        // TODO 회원id도 보내주자
        if(result){
            return new ResponseEntity<>(RsData.of(result), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(RsData.of(result), HttpStatus.UNAUTHORIZED);
        }
    }

}
