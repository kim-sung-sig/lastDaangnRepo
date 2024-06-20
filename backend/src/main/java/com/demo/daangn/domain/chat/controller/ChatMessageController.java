package com.demo.daangn.domain.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.daangn.global.dto.request.ScrollRequest;

@RestController
@RequestMapping("/api/v1/users/{userId}/chats/{chatRoomId}/messages/")
public class ChatMessageController {
    
    // 6. 채팅 메시지 가져오기
    @GetMapping("")
    public ResponseEntity<?> getChatMessages(@PathVariable("chatRoomId") Long chatRoomId, @RequestBody ScrollRequest sc) {
        try {
            return new ResponseEntity<>("GetChatMessages Result", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // 7. 채팅 메시지 저장하기 노필요 핸들러가 받아줌

    // 8. 채팅 메시지 수정하기
    @PutMapping("/{messageId}")
    public ResponseEntity<?> update() {
        try {
            return new ResponseEntity<>("Update Result", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 9. 채팅 메시지 삭제하기
    @DeleteMapping("/{messageId}")
    public ResponseEntity<?> deleteChatMessage() {
        try {
            return new ResponseEntity<>("Delete Result", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
