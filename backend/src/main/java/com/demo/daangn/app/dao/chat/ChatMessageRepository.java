package com.demo.daangn.app.dao.chat;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.daangn.app.domain.chat.message.ChatMessage;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID>, ChatMessageRepositoryCustom {

}
