package com.demo.daangn.app.dao.chat.room.jpa;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.daangn.app.domain.chat.room.ChatRoom;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {

    @EntityGraph(attributePaths = {"chatRoomUsers"})
    Optional<ChatRoom> findById(UUID id);

}
