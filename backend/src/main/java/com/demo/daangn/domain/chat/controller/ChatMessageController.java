package com.demo.daangn.domain.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.daangn.domain.chat.dto.request.ScrollRequest;
import com.demo.daangn.domain.chat.dto.response.ChatMessageResponse;
import com.demo.daangn.domain.chat.service.ChatMessageService;
import com.demo.daangn.global.dto.response.PagingResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 채팅 메시지 컨트롤러
 */
@RestController
@RequestMapping("/api/v1/users/{userId}/chats/{chatRoomId}/messages/") // TODO : URL 수정
@RequiredArgsConstructor
public class ChatMessageController {
    
    private final ChatMessageService chatMessageService;

    // 6. 채팅 메시지 가져오기
    @GetMapping("")
    public ResponseEntity<PagingResponse< ChatMessageResponse >> getChatMessages(
        @PathVariable("userId") Long userId,
        @PathVariable("chatRoomId") Long chatRoomId,
        @Valid @ModelAttribute ScrollRequest sc
    ) {
        try {
            PagingResponse<ChatMessageResponse> res = chatMessageService.getChatMessages(chatRoomId, userId, sc.getLastItemId(), sc.getPageSize());
            return new ResponseEntity<>(res, HttpStatus.OK);
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
