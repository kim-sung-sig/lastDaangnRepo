package com.demo.daangn.domain.notification.listener;

import java.util.List;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.demo.daangn.domain.chat.dto.response.ChatMessageResponse;
import com.demo.daangn.domain.chat.entity.ChatRoomEntity;
import com.demo.daangn.domain.chat.repository.ChatRoomUserRepository;
import com.demo.daangn.domain.event.event.ChatMessageEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationListener {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatRoomUserRepository chatRoomUserRepository;

    // 챗 메시지 이벤트가 발생하면!
    @EventListener
    public void handleChatMessageEvent(ChatMessageEvent event) {
        ChatMessageResponse response = event.getMessageResponse();
        ChatRoomEntity chatRoomEntity = ChatRoomEntity.builder().id(response.getChatRoomId()).build();

        List<Long> receivedUserIds = chatRoomUserRepository.findUserIdByChatRoom(chatRoomEntity);
        receivedUserIds.removeIf(num -> num.equals(response.getSender()));

        // TODO 여기서 알림을 저장시키고 해야댐!
        receivedUserIds.stream().forEach(receiver -> {
            messagingTemplate.convertAndSend("/sub/chat/alarm/" + receiver, response);
        });
    }

    //TODO 추가 증설 예정
    // 댓글 알림 이벤트가 발생하면!
    /*
    @EventListener
    public void handleCommentEvent(CommentEvent event) {
        CommentResponse response = event.getCommentResponse();
        messagingTemplate.convertAndSend("/sub/comment/alarm", response);
    }
    */
}
