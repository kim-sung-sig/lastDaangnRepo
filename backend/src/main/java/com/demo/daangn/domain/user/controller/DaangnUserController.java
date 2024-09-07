package com.demo.daangn.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.daangn.domain.user.dto.request.SignUpRequest;
import com.demo.daangn.domain.user.entity.DaangnUserEntity;
import com.demo.daangn.domain.user.service.DaangnUserService;
import com.demo.daangn.global.util.common.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/daangn/users")
@RequiredArgsConstructor
public class DaangnUserController {

    private final DaangnUserService daangnUserService;

    /**
     * 회원가입
     * @param signUpRequest
     * @return
     */
    @PostMapping(value = "/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest) {
        log.info("로그인 요청 => {}", signUpRequest);
        try {
            return new ResponseEntity<>("Create Result", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 회원 정보 수정 (정보 수정, 비밀번호 변경)
     * @param id
     * @param dto
     * @return
     */
    @PutMapping(value = "/update")
    public ResponseEntity<?> modified(HttpServletRequest request, @RequestBody Object dto) {
        try {
            log.info("회원 정보 수정 요청");
            DaangnUserEntity user = CommonUtil.getUser(request);
            Long userId = user.getId();
            return new ResponseEntity<>("Update Result", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 회원 정보 수정 (프로필 수정)
     * @param id
     * @param entity
     * @return
     */
    @PutMapping("/profile/update")
    public ResponseEntity<?> putMethodName(@PathVariable String id, @RequestBody String entity) {
        try {
            
            return new ResponseEntity<>("Update Result", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 회원 탈퇴
     * @param request
     * @return
     */
    @DeleteMapping(value = "")
    public ResponseEntity<?> destroy(HttpServletRequest request) {
        try {
            return new ResponseEntity<>("Destroy Result", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
