package com.demo.daangn.global.initdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.demo.daangn.domain.user.entity.DaangnUserEntity;
import com.demo.daangn.domain.user.repository.DaangnUserRepository;

@Configuration
public class InitData {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    CommandLineRunner init(DaangnUserRepository userRepository) {
        return args -> {
            // 유저
            DaangnUserEntity user = DaangnUserEntity.builder()
                    .username("test1")
                    .password(bCryptPasswordEncoder.encode("1234"))
                    .nickName("test1")
                    .role("ROLE_USER")
                    .isUsed(1)
                    .build();
            
            userRepository.save(user);
        };
    }

}
