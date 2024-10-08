package com.example.daangn;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.demo.daangn.BackendApplication;
import com.demo.daangn.domain.user.entity.DaangnUserEntity;
import com.demo.daangn.domain.user.repository.user.DaangnUserRepository;

import jakarta.persistence.EntityNotFoundException;


@SpringBootTest(classes = BackendApplication.class)
public class BcryptTest {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private DaangnUserRepository userRepository;

    @Test
        void bcryptTest(){
        DaangnUserEntity dbuser = userRepository.findById(1L)
                .orElseThrow(() -> new EntityNotFoundException());

        dbuser.updatePassword(bCryptPasswordEncoder.encode("password1!"));
        userRepository.save(dbuser);

        DaangnUserEntity dbuser2 = userRepository.findById(2L)
                .orElseThrow(() -> new EntityNotFoundException());

        dbuser2.updatePassword(bCryptPasswordEncoder.encode("password1!")); // setter 대신 update 메소드를 사용하여 변경 (이로서 SRP 원칙을 지킬 수 있다.)
        userRepository.save(dbuser2);
    }
}
