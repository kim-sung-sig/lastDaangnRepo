package com.demo.daangn.domain.config.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.daangn.domain.config.dto.response.NavigationItem;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class NavigationController {

    @GetMapping("/api/v1/navigation")
    public List<NavigationItem> getNavigation() {
        log.info("getNavigation");
        log.info("권한에 맞는거 필요함");
        return Arrays.asList(
            new NavigationItem("홈", "/", "home"),
            new NavigationItem("동네생활", "/life", "tab_group"),
            new NavigationItem("채팅", "/chats", "chat")
        );
    }
}
