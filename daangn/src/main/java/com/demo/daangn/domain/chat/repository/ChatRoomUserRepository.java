package com.demo.daangn.domain.chat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.daangn.domain.chat.entity.ChatRoomEntity;
import com.demo.daangn.domain.chat.entity.ChatRoomUserEntity;
import com.demo.daangn.domain.user.entity.DaangnUserEntity;

@Repository
public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUserEntity, Long> {

    Optional<ChatRoomUserEntity> findByUserAndChatRoom(DaangnUserEntity user, ChatRoomEntity chatRoom);

    @Query("select cu.user.id from ChatRoomUserEntity cu where cu.chatRoom = :chatRoomEntity")
    List<Long> findUserIdByChatRoom(ChatRoomEntity chatRoomEntity);
}
