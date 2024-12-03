package com.demo.daangn.domain.user.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.daangn.domain.email.service.EmailService;
import com.demo.daangn.domain.event.service.EventPublisherService;
import com.demo.daangn.domain.user.entity.User;
import com.demo.daangn.domain.user.entity.UserNickName;
import com.demo.daangn.domain.user.event.UserSignUpEvent;
import com.demo.daangn.domain.user.repository.nickname.UserNickNameRepository;
import com.demo.daangn.domain.user.repository.user.UserRepository;
import com.demo.daangn.domain.user.request.DaangnUserModifiedRequest;
import com.demo.daangn.domain.user.request.SignUpRequest;
import com.demo.daangn.global.exception.CustomBusinessException;
import com.demo.daangn.global.exception.CustomSystemException;
import com.demo.daangn.global.util.common.CommonUtil;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(value = "userService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserNickNameRepository userNickNameRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EventPublisherService eventPublisher;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Integer userSignUp(SignUpRequest signUpRequest){
        try {
            String username = signUpRequest.getUsername();
            String password = signUpRequest.getPassword();
            String email = signUpRequest.getEmail();
            String nickName = signUpRequest.getNickName();
            String token = signUpRequest.getToken();

            // 1. username 중복 체크
            userRepository.findByUsername(username).ifPresent(user -> {
                throw new CustomBusinessException("이미 존재하는 아이디입니다.");
            });

            // 2. 이메일 검증 체크 -> email regex + email DB 체크
            boolean emailCheck = emailService.verifyEmail(email, token);
            if (!emailCheck) {
                throw new CustomBusinessException("이메일 인증에 실패하였습니다. 잠시후 다시 시도해 주세요.");
            }

            // 3. 닉네임 seq 계산
            userNickNameRepository.save(UserNickName.builder().nickName(nickName).build());
            Long nickNameSeq = userNickNameRepository.countByNickName(nickName);

            // 4. 비밀번호 암호화
            String encrptedPassword = passwordEncoder.encode(password);

            // 5. 회원가입
            User user = User.builder()
                    .username(username)
                    .password(encrptedPassword)
                    .email(email)
                    .nickName(nickName)
                    .nickNameSeq(nickNameSeq)
                    .nickNameSeqFinal(nickName + "#_" + nickNameSeq)
                    .build();
            userRepository.save(user);

            // 6. 회원가입했음을 이벤트로 알림
            eventPublisher.publishEvent(new UserSignUpEvent(user));

            return 1;

        } catch (EntityNotFoundException | CustomBusinessException e){
            throw e;
        } catch (Exception e) {
            log.error("***********************************");
            log.error("** fail userSignUp");
            log.error("** LOG LINE : {}", e.getStackTrace()[0].getLineNumber());
            log.error("** LOG MSG : {}", e.getMessage());
            throw new CustomSystemException("회원가입 실패", e);
        }
    }

    /**
     * 탈퇴하기
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Integer userWithdrawal(){
        Integer result = 0;
        User user = CommonUtil.getLoginUser()
                .orElseThrow(() -> new CustomSystemException("로그인 정보가 없습니다."));
        user.deleteUser();
        userRepository.save(user);
        result = 1;
        return result;
    }

    @Override
    public Integer updateUserDetails(DaangnUserModifiedRequest userModifiedRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'userUpdateDetails'");
    }

}
