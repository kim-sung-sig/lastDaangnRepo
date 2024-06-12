package com.demo.daangn.global.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.daangn.domain.user.entity.DaangnUserEntity;
import com.demo.daangn.domain.user.repository.DaangnUserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDeailsService implements UserDetailsService {

    //@Autowired
    private final DaangnUserRepository daangnUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(" : " + username + "으로 호출");
        DaangnUserEntity user = daangnUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Unimplemented method 'loadUserByUsername'"));
        log.info("user => ", user);
        return new CustomUserDatails(user);
    }

}
