package com.demo.daangn.domain.chat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ChatMessageRequest {

    @NotEmpty
    private Long chatRoomId;

    @NotEmpty
    private Integer type;

    @NotEmpty
    private Long sender;

    @NotBlank
    private String content;
    
}
