package com.demo.daangn.domain.chat.service;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.demo.daangn.domain.chat.dto.response.ChatMessageResponse;
import com.demo.daangn.domain.chat.dto.response.ChatRoomResponse;
import com.demo.daangn.domain.chat.entity.ChatMessageEntity;
import com.demo.daangn.domain.chat.entity.ChatRoomEntity;
import com.demo.daangn.domain.chat.entity.ChatRoomUserEntity;
import com.demo.daangn.domain.chat.entity.QChatMessageEntity;
import com.demo.daangn.domain.chat.entity.QChatRoomEntity;
import com.demo.daangn.domain.chat.entity.QChatRoomUserEntity;
import com.demo.daangn.domain.chat.repository.ChatRoomRepository;
import com.demo.daangn.domain.chat.repository.ChatRoomUserRepository;
import com.demo.daangn.domain.user.entity.DaangnUserEntity;
import com.demo.daangn.domain.user.entity.QDaangnUserEntity;
import com.demo.daangn.domain.user.repository.DaangnUserRepository;
import com.demo.daangn.global.dto.response.PagingResponse;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

/** 채팅서비스 */
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final DaangnUserRepository userRepository;

    private final JPAQueryFactory queryFactory;

    // 1. 채팅방 만들기
    // 2. 채팅방 목록보기
    // 3. 채팅방 꾸미기
    // 4. 채팅방 나가기

    // 1. 채팅방 생성하기(post) 입장하기
    public Long createChatRoom(Long user1Id, Long user2Id) throws Exception { // user1Id가 방을 만드는 사람
        DaangnUserEntity user1 = userRepository.findById(user1Id).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));
        DaangnUserEntity user2 = userRepository.findById(user2Id).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));
        // 1. 기존 체팅방이 있는지 확인!
        List<Long> chatRoomsForUser1 = chatRoomUserRepository.findByUser(user1);
        List<Long> chatRoomsForUser2 = chatRoomUserRepository.findByUser(user2);

        chatRoomsForUser1.retainAll(chatRoomsForUser2);
        if(!chatRoomsForUser1.isEmpty()) {
            return chatRoomsForUser1.get(0);
        }
        // 없으면 새로운 채팅방 생성!
        ChatRoomEntity chatRoomEntity = new ChatRoomEntity();
        chatRoomEntity.setChatRoomCd(UUID.randomUUID().toString());
        chatRoomRepository.save(chatRoomEntity);
        
        List<ChatRoomUserEntity> savedEntity = new ArrayList<>();
        savedEntity.add( // 1. 방장
            ChatRoomUserEntity.builder()
                .chatRoom(chatRoomEntity)
                .user(user1)
                .isUsed(1)
                .build()
        );
        savedEntity.add( // 2. 참가자
            ChatRoomUserEntity.builder()
                .chatRoom(chatRoomEntity)
                .user(user2)
                .isUsed(0)
                .build()
        );
        chatRoomUserRepository.saveAll(savedEntity);

        // return
        return chatRoomEntity.getId();
    }

    // 2. 채팅방 목록가져오기(나의 채팅방)(get) // 일단 노페이징처리..
    public PagingResponse<ChatRoomResponse> getChatRooms(Long userId) throws Exception { // 페이징 어캐함?

        //TODO 여기도 querydsl에서 repo 방식으로 바꾸기
        DaangnUserEntity user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));

        QDaangnUserEntity qDaangnUserEntity = QDaangnUserEntity.daangnUserEntity;
        QChatRoomEntity qChatRoomEntity = QChatRoomEntity.chatRoomEntity;
        QChatRoomUserEntity qChatRoomUserEntity = QChatRoomUserEntity.chatRoomUserEntity;
        QChatMessageEntity qChatMessageEntity = QChatMessageEntity.chatMessageEntity;
        QChatMessageEntity qLastMessageEntity = new QChatMessageEntity("lastMessageEntity");

        List<Tuple> resultList = queryFactory
            .select(qChatRoomUserEntity, qChatRoomEntity, qLastMessageEntity)
            .from(qChatRoomUserEntity)
            .leftJoin(qChatRoomUserEntity.chatRoom, qChatRoomEntity).fetchJoin()
            .leftJoin(qChatRoomUserEntity.user, qDaangnUserEntity).fetchJoin()
            .leftJoin(qChatMessageEntity) // 라스트 메시지 가져오기
                .on(qChatMessageEntity.id.eq(
                    JPAExpressions
                        .select(qChatMessageEntity.id.max())
                        .from(qChatMessageEntity)
                        .where(qChatMessageEntity.room.eq(qChatRoomEntity))
                ))
            .where(
                qChatRoomUserEntity.user.eq(user),
                qChatRoomUserEntity.isUsed.eq(1)
            )
            .orderBy(qChatRoomEntity.modifiedDate.desc())
            .fetch();
        
        Long totalCount = queryFactory
            .select(qChatRoomUserEntity.id)
            .from(qChatRoomUserEntity)
            .where(
                qChatRoomUserEntity.user.eq(user),
                qChatRoomUserEntity.isUsed.eq(1)
            )
            .fetchOne();

        List<ChatRoomResponse> resList = resultList.stream()
                .map(tuple -> {
                    ChatRoomUserEntity chatRoomUserEntity = tuple.get(qChatRoomUserEntity);
                    ChatRoomEntity chatRoomEntity = tuple.get(qChatRoomEntity);
                    ChatMessageEntity lastMessageEntity = tuple.get(qLastMessageEntity);

                    ChatRoomResponse res = new ChatRoomResponse();
                    res.setChatRoomId(chatRoomEntity.getId());
                    res.setChatRoomCd(chatRoomEntity.getChatRoomCd());
                    res.setChatRoomName(chatRoomUserEntity.getChatRoomName());

                    // 상대방 정보 설정
                    List<ChatRoomUserEntity> usersInChatRoom = chatRoomUserRepository.findByChatRoom(chatRoomEntity);
                    DaangnUserEntity otherUser = usersInChatRoom.stream()
                            .map(ChatRoomUserEntity::getUser)
                            .filter(users -> !users.getId().equals(userId))
                            .findFirst()
                            .orElseThrow(() -> new IllegalStateException("상대방 정보를 찾을 수 없습니다."));
                    res.setUserId(otherUser.getId());
                    res.setUserName(otherUser.getNickName());
                    res.setUserProfile(otherUser.getUserProfile());

                    // 마지막 메시지 설정
                    if (lastMessageEntity != null) {
                        res.setLastMessage(new ChatMessageResponse(lastMessageEntity));
                    }
                    return res;
                })
                .collect(Collectors.toList());
        
        PagingResponse<ChatRoomResponse> response = new PagingResponse<>();
        response.setList(resList);
        response.setHasNext(false);
        response.setLastId(!resultList.isEmpty() ? resultList.get(resultList.size() - 1).get(qChatRoomUserEntity).getId() : null);
        response.setTotalCount(totalCount);

        return response;
    }

    // 3. 채팅방 커스텀하기(put) // 일단 여기는 채팅방 이름만 변경 나중에 추후 변경
    public void updateChatRoom(Long userId, Long chatRoomId, String chatRoomName) throws Exception {
        DaangnUserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));
        ChatRoomEntity chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 채팅방입니다."));
        ChatRoomUserEntity chatRoomUser = chatRoomUserRepository.findByUserAndChatRoom(user, chatRoom)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 채팅방입니다."));

        chatRoomUser.setChatRoomName(chatRoomName);// 일단은 request 없이 채팅방 이름 변경만 //TODO request 추가
        chatRoomUserRepository.save(chatRoomUser);
    }

    // 4. 채팅방 나가기(delete)
    public void leaveChatRoom(Long userId, Long chatRoomId) throws Exception {
        ChatRoomEntity chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 채팅방입니다."));
        DaangnUserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));
        ChatRoomUserEntity chatRoomUser = chatRoomUserRepository.findByUserAndChatRoom(user, chatRoom)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 채팅방입니다."));

        chatRoomUser.setIsUsed(0);
        chatRoomUserRepository.save(chatRoomUser);
    }
}
