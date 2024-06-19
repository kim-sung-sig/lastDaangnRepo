package com.demo.daangn.domain.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.daangn.domain.chat.entity.ChatMessageEntity;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long>, ChatMessageRepositoryCustom {

    // jpa
    



    // =======================================================================================
    // querydsl
    // =======================================================================================

    // find chat message
    // List<ChatMessageEntity> findChatMessage(Long chatRoomId, Long userId, Long lastId, int size) {

    // total count
    // Long findTotalCount(Long chatRoomId, Long userId);
}
