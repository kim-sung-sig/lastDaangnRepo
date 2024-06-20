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

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/users/{userId}/chats")
public class ChatController {

    // 1. 채팅방 목록가져오기
    @GetMapping()
    public ResponseEntity<?> getChatRoom(HttpSession session) {
        try {
            return new ResponseEntity<>("GetAll Results", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // 2. 채팅방 커스텀 정보 가져오기
    @GetMapping("/{chatRoomId}")
    public ResponseEntity<?> getOne(@PathVariable("chatRoomId") Long chatRoomId) {
        try {
            return new ResponseEntity<>("GetOne Result", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // 3. 채팅방 생성 요청
    @PostMapping()
    public ResponseEntity<?> createChatRoom() {
        try {
            return new ResponseEntity<>("Create Result", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // 4. 채팅방 커스텀하기
    @PutMapping("/{chatRoomId}")
    public ResponseEntity<?> updateChatRoom(@PathVariable("chatRoomId") Long chatRoomId) {
        try {
            return new ResponseEntity<>("Update Result", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // 5. 채팅방 떠나기
    @DeleteMapping("/{chatRoomId}")
    public ResponseEntity<?> leaveChatRoom(@PathVariable("chatRoomId") Long chatRoomId) {
        try {
            return new ResponseEntity<>("Destroy Result", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
