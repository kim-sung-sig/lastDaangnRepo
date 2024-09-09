package com.demo.daangn.domain.user.service;

import org.springframework.stereotype.Service;

import com.demo.daangn.domain.user.dto.request.DaangnUserModifiedRequest;
import com.demo.daangn.domain.user.dto.request.DaangnUserSignUpRequest;
import com.demo.daangn.global.exception.CustomBusinessException;
import com.demo.daangn.global.exception.CustomSystemException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(value = "daangnUserService")
@RequiredArgsConstructor
public class DaangnUserServiceImpl implements DaangnUserService {
    
    @Override
    public Integer userSignUp(HttpServletRequest request, DaangnUserSignUpRequest signUpRequest){
        try {
            throw new EntityNotFoundException("asf");
        
        } catch (EntityNotFoundException | CustomBusinessException e){
            throw e;
        } catch (Exception e) {
            log.error("fail userSignUp");
            e.printStackTrace();
            throw new CustomSystemException("회원가입 실패", e);
        }
    }

    @Override
    public Integer userUpdateDetails(HttpServletRequest request, DaangnUserModifiedRequest userModifiedRequest){
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'userUpdateDetails'");
    }

    @Override
    public Integer userWithdrawal(HttpServletRequest request){
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'userWithdrawal'");
    }

}
