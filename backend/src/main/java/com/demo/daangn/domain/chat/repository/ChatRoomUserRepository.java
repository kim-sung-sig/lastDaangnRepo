package com.demo.daangn.domain.chat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.daangn.domain.chat.entity.ChatRoomEntity;
import com.demo.daangn.domain.chat.entity.ChatRoomUserEntity;
import com.demo.daangn.domain.user.entity.DaangnUserEntity;

@Repository
public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUserEntity, Long> {

    Optional<ChatRoomUserEntity> findByUserAndChatRoom(DaangnUserEntity user, ChatRoomEntity chatRoom);

    @Query("select cu.user.id from ChatRoomUserEntity cu where cu.chatRoom = :chatRoomEntity")
    List<Long> findUserIdByChatRoom(ChatRoomEntity chatRoomEntity);

    @Query("select cu from ChatRoomUserEntity cu where cu.chatRoom = :chatRoomEntity")
    List<ChatRoomUserEntity> findByChatRoom(ChatRoomEntity chatRoomEntity);

    @Query("select cu.chatRoom.id from ChatRoomUserEntity cu where cu.user = :user")
    List<Long> findByUser(@Param("user") DaangnUserEntity user);

}
