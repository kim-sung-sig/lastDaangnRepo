package com.demo.daangn.domain.chat2.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomCreateRequest {

    private Long targetUserId; // 일단 유저 id만

    //TODO 중고거래 채팅방도 받는다.
}
