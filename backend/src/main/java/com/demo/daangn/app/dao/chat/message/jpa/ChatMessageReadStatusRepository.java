package com.demo.daangn.app.dao.chat.message.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.daangn.app.domain.chat.message.ChatMessageReadStatus;

@Repository
public interface ChatMessageReadStatusRepository extends JpaRepository<ChatMessageReadStatus, UUID> {

    @Query(value = """
            INSERT INTO dn_chat_message_read_status (id, message_id, user_id)
            VALUES (:#{#chatMessageReadStatus.id}, :#{#chatMessageReadStatus.chatMessage.id}, :#{#chatMessageReadStatus.user.id})
            ON CONFLICT (message_id, user_id) DO NOTHING
            """,
            nativeQuery = true)
    @Modifying
    void upsert(ChatMessageReadStatus chatMessageReadStatus);

}
