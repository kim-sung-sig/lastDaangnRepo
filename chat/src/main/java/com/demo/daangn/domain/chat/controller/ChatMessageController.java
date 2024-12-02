package com.demo.daangn.domain.chat.controller;

import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatMessageController {

    private final RabbitTemplate rabbitTemplate;


    @PostMapping(value = "/{chatRoomUuid}/message", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendMessage(@PathVariable UUID chatRoomUuid, @RequestBody Object message) {
        String methodName = "sendMessage";
        try {
            log.debug("[{}] chatRoomUuid => {}, message => {}", methodName, chatRoomUuid, message);
            rabbitTemplate.convertAndSend("daangn.chat.direct.exchange", "daangn.chat.routingKey", message);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("[{}] chatRoomUuid => {}, message => {}", methodName, chatRoomUuid, message, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/{chatRoomUuid}/message/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> sendMessageWithFile(@PathVariable UUID chatRoomUuid, @RequestPart("message") Object message, @RequestPart("file") MultipartFile[] file) {
        String methodName = "sendMessageWithFile";
        try {
            log.debug("[{}] chatRoomUuid => {}, message => {}, file => {}", methodName, chatRoomUuid, message, file);
            rabbitTemplate.convertAndSend("daangn.chat.direct.exchange", "daangn.chat.routingKey", message);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("[{}] chatRoomUuid => {}, message => {}, file => {}", methodName, chatRoomUuid, message, file, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
