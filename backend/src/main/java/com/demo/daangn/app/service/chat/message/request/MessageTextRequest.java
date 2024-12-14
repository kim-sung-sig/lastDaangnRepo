package com.demo.daangn.app.service.chat.message.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class MessageTextRequest extends MessageRequestBase {

    private String content;

}
