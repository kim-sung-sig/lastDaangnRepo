package com.demo.daangn.app.controller.api.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.daangn.app.service.user.UserService;
import com.demo.daangn.app.service.user.request.SignUpRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     * @param signUpRequest
     * @return
     */
    @PostMapping(value = "/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignUpRequest signUpRequest) {
        return new ResponseEntity<>("Create Result", HttpStatus.OK);
    }

    /**
     * 회원 탈퇴
     * @param request
     * @return
     */
    @DeleteMapping(value = "/withdrawal")
    public ResponseEntity<String> withdrawal() {
        return new ResponseEntity<>("Destroy Result", HttpStatus.OK);
    }

    /**
     * 회원 정보 수정 (정보 수정, 비밀번호 제외)
     * @param id
     * @param dto
     * @return
     */
    @PutMapping(value = "/{userUuid}")
    public ResponseEntity<?> modified(@PathVariable String userUuid, @RequestBody Object dto) {
        return new ResponseEntity<>("Update Result", HttpStatus.OK);
    }

    /**
     * 회원 정보 수정 (비밀번호 변경)
     * @param id
     * @param dto
     * @return
     */
    @PutMapping(value = "/{userUuid}/password")
    public ResponseEntity<?> updatePassword(@RequestBody Object dto) {
        return new ResponseEntity<>("Update Result", HttpStatus.OK);
    }

    /**
     * 회원 정보 수정 (비밀번호 fail count 초기화)
     * @param id
     * @param dto
     * @return
     */
    @PutMapping(value = "/{userUuid}/password/unlock")
    public ResponseEntity<?> updatePwdFailCnt(@RequestBody Object dto) {
        return new ResponseEntity<>("Update Result", HttpStatus.OK);
    }

    /**
     * 회원 정보 수정 (프로필 수정)
     * @param id
     * @param entity
     * @return
     */
    @PostMapping("/{userUuid}/profile")
    public ResponseEntity<?> putMethodName(@PathVariable("userUuid") String userUuid, @RequestBody String fileId) {
        return new ResponseEntity<>("Update Result", HttpStatus.OK);
    }

}
