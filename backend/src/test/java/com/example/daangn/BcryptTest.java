package com.example.daangn;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.demo.daangn.BackendApplication;
import com.demo.daangn.domain.user.entity.User;
import com.demo.daangn.domain.user.repository.user.UserRepository;

import jakarta.persistence.EntityNotFoundException;


@SpringBootTest(classes = BackendApplication.class)
public class BcryptTest {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Test
        void bcryptTest(){
        User dbuser = userRepository.findByUsername("test1")
                .orElseThrow(() -> new EntityNotFoundException());

        dbuser.setPassword(bCryptPasswordEncoder.encode("password1!"));
        userRepository.save(dbuser);

        User dbuser2 = userRepository.findByUsername("test2")
                .orElseThrow(() -> new EntityNotFoundException());

        dbuser2.setPassword(bCryptPasswordEncoder.encode("password1!")); // setter 대신 update 메소드를 사용하여 변경 (이로서 SRP 원칙을 지킬 수 있다.)
        userRepository.save(dbuser2);
    }
}
