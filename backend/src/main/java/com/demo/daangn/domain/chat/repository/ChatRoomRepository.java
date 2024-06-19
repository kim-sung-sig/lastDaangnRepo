package com.demo.daangn.domain.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.daangn.domain.chat.entity.ChatRoomEntity;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {

    // 1. 채팅방 생성하기

    // 2. 채팅방 숨기기? 흠

}
