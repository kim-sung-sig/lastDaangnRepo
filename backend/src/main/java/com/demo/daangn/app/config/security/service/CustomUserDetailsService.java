package com.demo.daangn.app.config.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.daangn.app.config.security.dto.CustomUserDatails;
import com.demo.daangn.app.dao.user.user.UserRepository;
import com.demo.daangn.app.domain.user.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    //@Autowired
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(" : " + username + "으로 호출");
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Unimplemented method 'loadUserByUsername'"));

        log.info("user => {}", user);
        return new CustomUserDatails(user);
    }

}
