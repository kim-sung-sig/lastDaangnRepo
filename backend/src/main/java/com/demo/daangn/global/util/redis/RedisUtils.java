package com.demo.daangn.global.util.redis;

import java.util.concurrent.TimeUnit;
import java.util.Optional;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisUtils {
    
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * Redis에 데이터를 저장합니다. (만료 시간이 있는 경우)
     *
     * @param prefix Redis 키의 프리픽스 (예: "post", "user" 등)
     * @param key Redis에 저장할 키 (프리픽스를 포함하지 않은 값)
     * @param value Redis에 저장할 값
     * @param expirationTime 데이터의 만료 시간 (초 단위)
     */
    public void set(String prefix, String key, Object value, long expirationTime) {
        String redisKey = buildRedisKey(prefix, key);
        redisTemplate.opsForValue().set(redisKey, value, expirationTime, TimeUnit.SECONDS);
    }
    
    /**
     * Redis에 데이터를 저장합니다. (만료 시간이 없는 경우)
     *
     * @param prefix Redis 키의 프리픽스 (예: "post", "user" 등)
     * @param key Redis에 저장할 키 (프리픽스를 포함하지 않은 값)
     * @param value Redis에 저장할 값
     * @param expirationTime 데이터의 만료 시간 (초 단위)
     */
    public void set(String prefix, String key, Object value) {
        String redisKey = buildRedisKey(prefix, key);
        redisTemplate.opsForValue().set(redisKey, value);
    }

    /**
     * Redis에서 데이터를 조회합니다.
     *
     * @param prefix Redis 키의 프리픽스
     * @param key Redis에서 조회할 키 (프리픽스를 포함하지 않은 값)
     * @return Redis에 저장된 값, 없으면 null
     */
    public <T> Optional<T> get(String prefix, String key, Class<T> type) {
        String redisKey = buildRedisKey(prefix, key);
        Object value = redisTemplate.opsForValue().get(redisKey);

        if(value != null && type.isInstance(value)) {
            return Optional.of(type.cast(value));
        }
        return Optional.empty();
    }

    /**
     * Redis에서 데이터를 삭제합니다.
     *
     * @param prefix Redis 키의 프리픽스
     * @param key Redis에서 삭제할 키 (프리픽스를 포함하지 않은 값)
     */
    public void delete(String prefix, String key) {
        String redisKey = buildRedisKey(prefix, key);
        redisTemplate.delete(redisKey);
    }

    /**
     * Redis 키를 생성하는 유틸리티 메서드.
     *
     * @param prefix 프리픽스
     * @param key 키
     * @return 완성된 Redis 키 (prefix_key 형식)
     */
    private String buildRedisKey(String prefix, String key) {
        return prefix + "_" + key;
    }

}
