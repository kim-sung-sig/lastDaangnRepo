package com.demo.daangn.domain.user.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.daangn.domain.email.service.EmailService;
import com.demo.daangn.domain.event.service.EventPublisherService;
import com.demo.daangn.domain.user.dto.event.UserSignUpEvent;
import com.demo.daangn.domain.user.dto.request.DaangnUserModifiedRequest;
import com.demo.daangn.domain.user.dto.request.DaangnUserSignUpRequest;
import com.demo.daangn.domain.user.entity.DaangnUserEntity;
import com.demo.daangn.domain.user.entity.DaangnUserNickNameEntity;
import com.demo.daangn.domain.user.repository.nickname.DaangnUserNickNameRepository;
import com.demo.daangn.domain.user.repository.user.DaangnUserRepository;
import com.demo.daangn.global.exception.CustomBusinessException;
import com.demo.daangn.global.exception.CustomSystemException;
import com.demo.daangn.global.util.common.CommonUtil;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(value = "userService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final DaangnUserRepository daangnUserRepository;
    private final DaangnUserNickNameRepository daangnUserNickNameRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EventPublisherService eventPublisher;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Integer userSignUp(HttpServletRequest request, DaangnUserSignUpRequest signUpRequest){
        try {
            String username = signUpRequest.getUsername();
            String password = signUpRequest.getPassword();
            String email = signUpRequest.getEmail();
            String nickName = signUpRequest.getNickName();
            String token = signUpRequest.getToken();

            // 1. username 중복 체크
            daangnUserRepository.findByUsername(username).ifPresent(user -> {
                throw new CustomBusinessException("이미 존재하는 아이디입니다.");
            });

            // 2. 이메일 검증 체크 -> email regex + email DB 체크
            boolean emailCheck = emailService.verifyEmail(email, token);
            if (!emailCheck) {
                throw new CustomBusinessException("이메일 인증에 실패하였습니다. 잠시후 다시 시도해 주세요.");
            }

            // 3. 닉네임 seq 계산
            daangnUserNickNameRepository.save(DaangnUserNickNameEntity.builder().nickName(nickName).build());
            Long nickNameSeq = daangnUserNickNameRepository.countByNickName(nickName);

            // 4. 비밀번호 암호화
            String encrptedPassword = passwordEncoder.encode(password);

            // 5. 회원가입
            DaangnUserEntity user = DaangnUserEntity.builder()
                    .username(username)
                    .password(encrptedPassword)
                    .email(email)
                    .nickName(nickName)
                    .nickNameSeq(nickNameSeq)
                    .nickNameSeqFinal(nickName + "#_" + nickNameSeq)
                    .build();
            daangnUserRepository.save(user);

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
    public Integer userWithdrawal(HttpServletRequest request){
        try {
            Integer result = 0;
            DaangnUserEntity user = CommonUtil.getUser(request); // 이미 컨트롤러에서 한번 꺼내봄 ㄱㅊㄱㅊ
            user.setIsUsed(0);
            daangnUserRepository.save(user);
            result = 1;
            return result;

        } catch (Exception e) {
            log.error("fail userWithdrawal");
            e.printStackTrace();
            throw new CustomSystemException("회원탈퇴 실패", e);
        }
    }

    @Override
    public Integer userUpdateDetails(HttpServletRequest request, DaangnUserModifiedRequest userModifiedRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'userUpdateDetails'");
    }

}
