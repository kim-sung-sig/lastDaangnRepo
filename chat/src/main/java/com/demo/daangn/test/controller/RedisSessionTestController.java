package com.demo.daangn.test.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RedisSessionTestController {

    @GetMapping
    public ResponseEntity<String> test() {
        log.info("test");
        return ResponseEntity.ok("test");
    }

}
