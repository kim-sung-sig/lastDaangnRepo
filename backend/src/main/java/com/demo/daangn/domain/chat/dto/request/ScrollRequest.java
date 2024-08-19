package com.demo.daangn.domain.chat.dto.request;

import com.demo.daangn.global.dto.request.BaseScrollRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true) // 부모 클래스의 필드도 toString에 포함
@Builder
public class ScrollRequest extends BaseScrollRequest {

    private Long chatRoomId;

    public Long getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(Long chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

}
