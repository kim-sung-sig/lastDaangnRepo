package com.demo.daangn.domain.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.daangn.domain.chat.service.ChatRoomService;
import com.demo.daangn.domain.user.entity.DaangnUserEntity;
import com.demo.daangn.global.util.common.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/daangn/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatRoomService chatRoomService;

    // 채팅방 목록 조회
    @GetMapping()
    public ResponseEntity<?> getChatRoomList(HttpServletRequest request) {
        try {
            DaangnUserEntity user = CommonUtil.getUser(request);
            Long userId = user.getId();

            //chatRoomService.getChatRoomList(userId);
            return new ResponseEntity<>("GetAll Results", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 채팅방 입장 여부 확인
    @GetMapping("/check/{id}")
    public ResponseEntity<?> checkChatRoom(HttpServletRequest request, @PathVariable Integer id) {
        try {
            return new ResponseEntity<>("Check Result", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getChatRoom(HttpServletRequest request, @PathVariable Integer id) {
        try {
            return new ResponseEntity<>("GetOne Result", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<?> createChatRoom(HttpServletRequest request) {
        try {
            return new ResponseEntity<>("Create Result", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    public ResponseEntity<?> modifiedChatRoom(HttpServletRequest request) {
        try {
            return new ResponseEntity<>("Update Result", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroyChatRoom(HttpServletRequest request) {
        try {
            return new ResponseEntity<>("Destroy Result", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
