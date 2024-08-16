package com.example.daangn;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.demo.daangn.BackendApplication;
import com.demo.daangn.domain.user.entity.DaangnUserEntity;
import com.demo.daangn.domain.user.repository.DaangnUserRepository;

import jakarta.persistence.EntityNotFoundException;


@SpringBootTest(classes = BackendApplication.class)
public class BcryptTest {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private DaangnUserRepository userRepository;

    @Test
    void bcryptTest(){
        DaangnUserEntity user = userRepository.findById(1L)
                .orElseThrow(() -> new EntityNotFoundException());

        user.setPassword(bCryptPasswordEncoder.encode("password1!"));
        
        userRepository.save(user);
        DaangnUserEntity user2 = userRepository.findById(2L)
                .orElseThrow(() -> new EntityNotFoundException());

        user2.setPassword(bCryptPasswordEncoder.encode("password1!"));
        
        userRepository.save(user2);
    }
}
