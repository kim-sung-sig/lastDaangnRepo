package com.demo.daangn.auth.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.daangn.auth.app.service.TestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/test")
    public String test() throws Exception {
        testService.test();
        return "test";
    }
}
