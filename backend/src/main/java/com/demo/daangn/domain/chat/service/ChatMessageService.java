package com.demo.daangn.domain.chat.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.demo.daangn.domain.chat.dto.response.ChatMessageResponse;
import com.demo.daangn.domain.chat.entity.ChatMessageEntity;
import com.demo.daangn.domain.chat.entity.ChatRoomEntity;
import com.demo.daangn.domain.chat.repository.ChatMessageRepository;
import com.demo.daangn.domain.chat.repository.ChatRoomRepository;
import com.demo.daangn.domain.chat.repository.ChatRoomUserRepository;
import com.demo.daangn.domain.user.entity.DaangnUserEntity;
import com.demo.daangn.domain.user.repository.user.DaangnUserRepository;
import com.demo.daangn.global.dto.response.PagingResponse;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final DaangnUserRepository userRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    
    // 1. 쳇 메시지 얻기
    public PagingResponse<ChatMessageResponse> getChatMessages(Long chatRoomId, Long usedId, Long lastId, int size) throws Exception {
        // validation
        if((lastId != null && lastId < 0) || size < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "유효하지않은 lastId or size");
        }
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 채팅방입니다."));
        DaangnUserEntity userEntity = userRepository.findById(usedId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));
        chatRoomUserRepository.findByUserAndChatRoom(userEntity, chatRoomEntity)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "채팅방에 참여하지 않은 유저입니다."));
        // end of validation
        
        // 1. list
        List<ChatMessageEntity> messages = chatMessageRepository.findChatMessage(chatRoomId, usedId, lastId, size);

        // 2. total count
        Long totalCount = chatMessageRepository.findTotalCount(chatRoomId, usedId);

        PagingResponse<ChatMessageResponse> response = new PagingResponse<>();
        response.setList(messages.stream().map(ChatMessageResponse::new).toList());
        response.setTotalCount(totalCount);
        response.setLastId(!messages.isEmpty() ? messages.get(messages.size() - 1).getId() : null);
        response.setHasNext(messages.size() == size);
        return response;
    }
    
    // 2. 챗 메시지 저장하기 valid만 체크하면 될듯?
    @Transactional
    public Boolean saveChatMessage() {
        return null;
    }

    @Transactional
    public Boolean saveChatMessgeType3(){ // 이런식으로 대충 확장해보자.
        return null;
    }
    // 3. 쳇 메시지 수정하기 valid만 체크하면 될듯?
    public Boolean updateChatMessage() {
        return null;
    }
    // 4. 쳇 메시지 삭제하기
    public Boolean deleteChatMessage(Long chatMessageId) throws Exception {
        ChatMessageEntity chatMessageEntity = chatMessageRepository.findById(chatMessageId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 메시지입니다."));
        if(chatMessageEntity.getIsUsed().equals(0)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 삭제된 메시지입니다.");
        }
        chatMessageEntity.setIsUsed(0);
        chatMessageRepository.save(chatMessageEntity);
        return true;
    }

}
