package com.demo.daangn.domain.chat.repository;

import java.util.List;

import com.demo.daangn.domain.chat.entity.ChatMessageEntity;

public interface ChatMessageRepositoryCustom {

    /**
     * 채팅방의 메시지를 조회한다.
     * @param chatRoomId
     * @param userId
     * @param lastId
     * @param size
     * @return
     */
    List<ChatMessageEntity> findChatMessage(Long chatRoomId, Long userId, Long lastId, int size);

    /**
     * 채팅방의 총 메시지 수를 조회한다.
     * @param chatRoomId
     * @param userId
     * @return
     */
    Long findTotalCount(Long chatRoomId, Long userId);
}
