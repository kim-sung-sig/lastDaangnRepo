package com.demo.daangn.app.service.chat.message.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class MessageWithFileRequest extends MessageRequestBase {

    private MultipartFile[] file;

}
