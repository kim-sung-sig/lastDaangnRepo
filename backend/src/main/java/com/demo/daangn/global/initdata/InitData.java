package com.demo.daangn.global.initdata;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.demo.daangn.domain.user.entity.DaangnUserEntity;
import com.demo.daangn.domain.user.repository.DaangnUserRepository;

@Configuration
public class InitData {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // @Bean
    CommandLineRunner init(DaangnUserRepository userRepository) {
        return args -> {
            // 유저
            DaangnUserEntity user1 = DaangnUserEntity.builder()
                    .username("test1")
                    .password(bCryptPasswordEncoder.encode("password1!"))
                    .role("ROLE_ADMIN")
                    .nickName("테스트계정1")
                    .nickNameSeq(1L)
                    .nickNameSeqFinal("테스트계정1#1")
                    .role("ROLE_USER")
                    .build();

            DaangnUserEntity user2 = DaangnUserEntity.builder()
                    .username("test2")
                    .password(bCryptPasswordEncoder.encode("password1!"))
                    .role("ROLE_ADMIN")
                    .nickName("테스트계정2")
                    .nickNameSeq(1L)
                    .nickNameSeqFinal("테스트계정2#1")
                    .role("ROLE_USER")
                    .build();
            
            List<DaangnUserEntity> users = List.of(user1, user2);
            userRepository.saveAll(users);
        };
    }

}
