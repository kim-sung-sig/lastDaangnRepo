package com.demo.daangn.domain.chat.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RestControllerAdvice(basePackageClasses = ChatRoomController.class)
public class ChatRoomController {

    
}
