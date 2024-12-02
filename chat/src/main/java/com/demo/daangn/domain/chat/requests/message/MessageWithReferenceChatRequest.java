package com.demo.daangn.domain.chat.requests.message;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class MessageWithReferenceChatRequest extends MessageTextRequest{

    private UUID referenceMessageUuid;

}
