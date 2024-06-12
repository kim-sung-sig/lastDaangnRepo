package com.demo.daangn.domain.notification.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO 알림 만들어야댐
@RestController
@RequestMapping("/api/user")
public class NotificationController {

    /**
     * 알림 패이징처리
     * @param userId
     * @return
     */
    @GetMapping("{userId}/notifications")
    public ResponseEntity<?> getAll(@PathVariable("userId") Long userId) {
        try {
            return new ResponseEntity<>("GetAll Results", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * 알림하나얻기
     * @param userId
     * @param notificationId
     * @return
     */
    @GetMapping("/{userId}/notifications/{notificationId}")
    public ResponseEntity<?> getOne(@PathVariable("userId") Long userId, @PathVariable("notificationId") Long notificationId) {
        try {
            return new ResponseEntity<>("GetOne Result", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // 알림은 포스트가 필요없다!

    /**
     * 알림 모두 읽음 처리
     * @param dto
     * @return
     */
    @PutMapping("/{userId}/notifications")
    public ResponseEntity<?> updateAll(@PathVariable("usedId") Long usedId) {
        try {
            return new ResponseEntity<>("Update Result", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 알림 하나 수정하기 .. 어떤식일지는 모르겟음!!
     * @param dto
     * @return
     */
    @PutMapping("/{userId}/notifications/{notificationId}")
    public ResponseEntity<?> updateByNotificationId(
            @PathVariable("usedId") Long usedId,
            @PathVariable("notificationId") Long notificationId
    ) {

        try {
            return new ResponseEntity<>("Update Result", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * 알림 가리기
     * @param id
     * @return
     */
    @DeleteMapping("/{userId}/notifications/{notificationId}")
    public ResponseEntity<?> destroy(@PathVariable("userId") Long userId, @PathVariable("notificationId") Long notificationId) {
        try {
            return new ResponseEntity<>("Destroy Result", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
