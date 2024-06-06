package com.demo.daangn.global.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.daangn.domain.user.entity.DaangnUserEntity;
import com.demo.daangn.domain.user.repository.DaangnUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDeailsService implements UserDetailsService {

    //@Autowired
    private final DaangnUserRepository daangnUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DaangnUserEntity user = daangnUserRepository.findByUsername(username)
                .orElseThrow(() -> new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'"));

        return new CustomUserDatails(user);
    }

}
