package com.demo.daangn.domain.chat.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.demo.daangn.domain.chat.entity.ChatMessageEntity;
import com.demo.daangn.domain.chat.entity.QChatMessageEntity;
import com.demo.daangn.domain.chat.entity.QChatRoomEntity;
import com.demo.daangn.domain.chat.entity.QChatRoomUserEntity;
import com.demo.daangn.domain.user.entity.QDaangnUserEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepositoryCustomImpl implements ChatMessageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ChatMessageEntity> findChatMessage(Long chatRoomId, Long userId, Long lastId, int size) {
        
        // pagination
        Pageable pageable = PageRequest.of(0, size);

        // QueryDSL
        QChatMessageEntity chatMessage = QChatMessageEntity.chatMessageEntity;
        QChatRoomEntity chatRoom = QChatRoomEntity.chatRoomEntity;
        QChatRoomUserEntity chatRoomUser = QChatRoomUserEntity.chatRoomUserEntity;
        QDaangnUserEntity user = QDaangnUserEntity.daangnUserEntity;

        return queryFactory
                .select(chatMessage)
                .from(chatMessage)
                .leftJoin(user).on(chatRoomUser.user.id.eq(user.id)).fetchJoin()
                .leftJoin(chatRoom).on(chatMessage.room.id.eq(chatRoom.id)).fetchJoin()
                .leftJoin(chatRoomUser).on(chatRoomUser.chatRoom.id.eq(chatRoom.id)).fetchJoin()
                .where(
                    lastId != null ? chatMessage.id.lt(lastId) : null,
                    user.id.eq(userId),
                    chatRoomUser.cursor.gt(chatMessage.id),
                    chatRoom.id.eq(chatRoomId)
                )
                .orderBy(chatMessage.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public Long findTotalCount(Long chatRoomId, Long userId) {
        QChatMessageEntity chatMessage = QChatMessageEntity.chatMessageEntity;
        QChatRoomEntity chatRoom = QChatRoomEntity.chatRoomEntity;
        QChatRoomUserEntity chatRoomUser = QChatRoomUserEntity.chatRoomUserEntity;
        QDaangnUserEntity user = QDaangnUserEntity.daangnUserEntity;

        return queryFactory
                .select(chatMessage.count())
                .from(chatMessage)
                .leftJoin(user).on(chatRoomUser.user.id.eq(user.id)).fetchJoin()
                .leftJoin(chatRoom).on(chatMessage.room.id.eq(chatRoom.id)).fetchJoin()
                .leftJoin(chatRoomUser).on(chatRoomUser.chatRoom.id.eq(chatRoom.id)).fetchJoin()
                .where(
                    user.id.eq(userId),
                    chatRoom.id.eq(chatRoomId),
                    chatRoomUser.cursor.gt(chatMessage.id)
                )
                .fetchOne();
    }

}
