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

import com.demo.daangn.domain.chat.dto.request.ChatRoomCreateRequest;
import com.demo.daangn.domain.chat.dto.request.ChatRoomUpdateRequest;
import com.demo.daangn.domain.chat.dto.response.ChatRoomResponse;
import com.demo.daangn.domain.chat.service.ChatService;
import com.demo.daangn.domain.user.entity.DaangnUserEntity;
import com.demo.daangn.domain.user.repository.DaangnUserRepository;
import com.demo.daangn.global.dto.response.PagingResponse;
import com.demo.daangn.global.dto.response.RsData;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users/{userId}/chats")
public class ChatController {

    private final ChatService chatService;
    private final DaangnUserRepository daangnUserRepository;

    // 1. 채팅방 목록가져오기
    @GetMapping()
    public ResponseEntity<PagingResponse< ChatRoomResponse >> getChatRooms(HttpSession session,
        @PathVariable("userId") Long userId
    ) {
        try {
            DaangnUserEntity sessionUser = (DaangnUserEntity) session.getAttribute("user");
            DaangnUserEntity requestUser = daangnUserRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("해당 사용자가 없습니다."));
            if(sessionUser.getId() != requestUser.getId()) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            return new ResponseEntity<>(chatService.getChatRooms(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // 2. 채팅방 입장하기 & 채팅방 커스텀 정보 가져오기
    @GetMapping("/{chatRoomId}")
    public ResponseEntity<RsData< ChatRoomResponse >> getChatRoom(HttpSession session,
        @PathVariable("userId") Long userId,
        @PathVariable("chatRoomId") Long chatRoomId
    ) {
        try {
            DaangnUserEntity sessionUser = (DaangnUserEntity) session.getAttribute("user");
            DaangnUserEntity requestUser = daangnUserRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("해당 사용자가 없습니다."));
            if(sessionUser.getId() != requestUser.getId()) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            if(!chatService.isAvailableChatRoom(userId, chatRoomId)){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            ChatRoomResponse chatRoom = chatService.getChatRoom(userId, chatRoomId);
            return new ResponseEntity<>(RsData.of("succes", chatRoom), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // 3. 채팅방 생성 요청
    @PostMapping()
    public ResponseEntity<?> createChatRoom(HttpSession session,
        @PathVariable("userId") Long userId,
        @RequestBody ChatRoomCreateRequest req // TODO : RequestBody 수정
    ) {
        try {
            chatService.createChatRoom(userId, req);
            return new ResponseEntity<>("Create Result", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // 4. 채팅방 커스텀하기
    @PutMapping("/{chatRoomId}")
    public ResponseEntity<RsData< Boolean >> updateChatRoom(HttpSession session,
        @PathVariable("userId") Long userId,
        @PathVariable("chatRoomId") Long chatRoomId,
        @RequestBody ChatRoomUpdateRequest req // TODO : RequestBody 수정
    ) {
        try {
            Boolean result = chatService.updateChatRoom(userId, chatRoomId, req);
            return new ResponseEntity<>(RsData.of(result), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // 5. 채팅방 떠나기
    @DeleteMapping("/{chatRoomId}")
    public ResponseEntity<RsData< Boolean >> leaveChatRoom(HttpSession session,
        @PathVariable("userId") Long userId,
        @PathVariable("chatRoomId") Long chatRoomId
    ) {
        try {
            Boolean result = chatService.leaveChatRoom(userId, chatRoomId);
            return new ResponseEntity<>(RsData.of(result), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
