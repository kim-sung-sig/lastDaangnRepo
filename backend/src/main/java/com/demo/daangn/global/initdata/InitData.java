package com.demo.daangn.global.initdata;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.demo.daangn.domain.user.entity.User;
import com.demo.daangn.domain.user.repository.user.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class InitData {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Bean
    CommandLineRunner init() {
        return args -> {
            // 유저
            userRepository.findByUsername("test1").ifPresentOrElse(
                    user -> log.debug("user1: {}", user),
                    () -> {
                        User user1 = User.builder()
                                .username("test1")
                                .password(bCryptPasswordEncoder.encode("password1!"))
                                .role("ROLE_ADMIN")
                                .email("test")
                                .nickName("테스트계정1")
                                .nickNameSeq(1L)
                                .nickNameSeqFinal("테스트계정1 #_1")
                                .build();
                        log.debug("user1: {}", user1);
                        userRepository.save(user1);
                    }
            );
            
            userRepository.findByUsername("test2").ifPresentOrElse(
                    user -> log.debug("user2: {}", user),
                    () -> {
                        User user2 = User.builder()
                                .username("test2")
                                .password(bCryptPasswordEncoder.encode("password1!"))
                                .role("ROLE_ADMIN")
                                .email("test")
                                .nickName("테스트계정2")
                                .nickNameSeq(1L)
                                .nickNameSeqFinal("테스트계정2 #_1")
                                .build();
                        userRepository.save(user2);
                    }
            );

            redisTemplate.opsForValue().set("test", "test", 60, TimeUnit.SECONDS);
        };
    }

}
