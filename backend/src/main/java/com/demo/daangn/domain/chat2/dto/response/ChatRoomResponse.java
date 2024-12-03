package com.demo.daangn.domain.chat2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoomResponse {

    private Long chatRoomId;
    private String chatRoomCd; //UUID
    private String chatRoomName;

    private Long userId;
    private String userName;
    private String userProfile;

    private ChatMessageResponse lastMessage;
    private Long unreadMessageCount;

    // TODO 이것도 해야되네 하..
    private Long boardId;
    private String boardThumbnail;

}
