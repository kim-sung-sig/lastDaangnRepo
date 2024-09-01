package com.demo.daangn.domain.chat.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.daangn.domain.chat.entity.ChatRoomEntity;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long>, ChatRoomRepositoryCustom {

    @Query("SELECT c FROM ChatRoomEntity c WHERE c.id = :id")
    Optional<ChatRoomEntity> findById(@Param("id") Long id);

    @Query("SELECT c FROM ChatRoomEntity c LEFT JOIN FETCH c.chatRoomUsers WHERE c.id = :id")
    Optional<ChatRoomEntity> findByIdWithChatRoomUser(@Param("id") Long id);

    // 1. 채팅방 생성하기
    // save
    // 2. 채팅방 숨기기? 흠
    // save isUsed = 0;
}
