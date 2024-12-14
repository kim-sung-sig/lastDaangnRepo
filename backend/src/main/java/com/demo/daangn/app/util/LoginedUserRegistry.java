package com.demo.daangn.app.util;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.demo.daangn.app.config.security.dto.SessionDestroyedEvent;
import com.demo.daangn.app.config.security.dto.UserLoggedInEvent;
import com.demo.daangn.app.domain.user.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginedUserRegistry {

    private final RedisTemplate<String, Object> redisTemplate;

    private final String REDIS_KEY = "daangn:logined";

    /**
     * 사용자 로그인 이벤트 (등록)
     * @param event
     */
    @EventListener
    public void userLoggedInEvent(UserLoggedInEvent event) {
        log.debug("User Logged In");
        User loginUser = event.getUser();
        redisTemplate.opsForSet().add(REDIS_KEY, loginUser.getId());
    }

    /**
     * 세션 종료 이벤트 (삭제)
     * @param event
     */
    @EventListener
    public void handleSessionDestroyedEvent(SessionDestroyedEvent event) {
        log.debug("Session Destroyed");
        redisTemplate.opsForSet().remove(REDIS_KEY, event.getUser().getId());
    }

    /**
     * 로그인된 사용자 목록 조회 (조회)
     * @return
     */
    public Set<UUID> getLoginedUsers() {
        return redisTemplate.opsForSet().members(REDIS_KEY).stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .map(o -> UUID.fromString(o))
                .collect(Collectors.toSet());
    }

}
