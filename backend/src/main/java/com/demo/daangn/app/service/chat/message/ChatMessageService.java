package com.demo.daangn.app.service.chat.message;

import java.util.UUID;

import com.demo.daangn.app.service.chat.message.request.MessageTextRequest;
import com.demo.daangn.app.service.chat.message.request.MessageWithFileRequest;

public interface ChatMessageService {

    // 1.1. 채팅 메시지 전송 하기
    void sendMessage(UUID chatRoomUuid, MessageTextRequest message);

    // 1.2. 채팅 메시지 전송 하기 with 파일(이미지, 동영상, 음성 등)
    void sendMessageWithFile(UUID chatRoomUuid, MessageWithFileRequest message);

    // 1.3. 채팅 메시지 전송 하기 with 캘린더 일정

    // 1.4. 채팅 메시지 답변달기
    

    // 1.99 아래로 추가



    // 2. 채팅메시지 조회하기 (채팅 이력 조회)


    // TODOs 채팅 메시지 수정, 삭제 기능 추가

}
