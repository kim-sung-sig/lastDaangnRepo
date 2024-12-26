package com.demo.daangn.app.service.user;

import java.time.LocalDateTime;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.daangn.app.common.exception.AuthException;
import com.demo.daangn.app.common.exception.CustomBusinessException;
import com.demo.daangn.app.config.security.dto.UserLoggedInEvent;
import com.demo.daangn.app.dao.temp.nickname.UserNickNameRepository;
import com.demo.daangn.app.dao.user.user.UserRepository;
import com.demo.daangn.app.domain.temp.nickname.UserNickName;
import com.demo.daangn.app.domain.user.User;
import com.demo.daangn.app.service.auth.email.EmailService;
import com.demo.daangn.app.service.user.event.UserSignUpEvent;
import com.demo.daangn.app.service.user.request.DaangnUserModifiedRequest;
import com.demo.daangn.app.service.user.request.SignUpRequest;
import com.demo.daangn.app.util.CommonUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserNickNameRepository userNickNameRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;

    // 회원가입
    // 1. 회원가입 하기
    @Transactional(rollbackFor = {Exception.class})
    public Integer userSignUp(SignUpRequest signUpRequest){
        // 유효성 검사
        String username = signUpRequest.getUsername();
        String password = signUpRequest.getPassword();
        String email = signUpRequest.getEmail();
        String nickName = signUpRequest.getNickName();
        String token = signUpRequest.getToken();

        if (!CommonUtil.isNotBlank(username, password, email, nickName, token)) {
            log.warn("필수값 누락 : {}", signUpRequest);
            throw new CustomBusinessException("필수값이 누락되었습니다.");
        }

        // 1. username 중복 체크
        userRepository.findByUsername(username)
                .ifPresentOrElse(
                    user -> {
                        log.warn("username 중복 : {}", username);
                        throw new CustomBusinessException("이미 존재하는 아이디입니다.");
                    }
                    , () -> { log.debug("username 사용가능 : {}", username); }
                );

        // 2. 이메일 검증 체크 -> email regex + email DB 체크
        boolean emailCheck = emailService.verifyEmail(email, token);
        if (!emailCheck) {
            log.warn("이메일 인증 실패 : {}", email);
            throw new CustomBusinessException("이메일 인증에 실패하였습니다. 이메일 인증 후 다시 시도해 주세요.");
        }

        // 3. 닉네임 seq 계산
        userNickNameRepository.save(UserNickName.builder().nickName(nickName).build());
        Long nickNameSeq = userNickNameRepository.countByNickName(nickName);

        // 4. 회원가입
        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .nickName(nickName)
                .nickNameSeq(nickNameSeq)
                .nickNameSeqFinal(nickName + "#_" + nickNameSeq)
                .build();
        userRepository.save(user);

        // 6. 회원가입했음을 이벤트로 알림
        eventPublisher.publishEvent(new UserSignUpEvent(user));

        return 1;
    }

    // 2 회원정보조회
    // 2.1 회원정보조회
    // 2.1.1 회원정보조회(my)
    // 2.2 회원정보목록조회

    // 3. 회원 정보 수정하기 및 비밀번호 변경하기
    @Transactional(rollbackFor = {Exception.class})
    public Integer updateUserDetails(DaangnUserModifiedRequest userModifiedRequest) {
        throw new UnsupportedOperationException("Unimplemented method 'userUpdateDetails'");
    }

    // 3.1 회원 정보 수정하기(비밀 번호 제외)
    // 3.2 비밀번호 변경하기 (마이페이지에서)
    // 3.3 비밀번호 변경 시 메일 발송하기
    // 3.4 비밀 번호 변경 시 메일 인증하기

    // 3.3 비밀번호 찾기 및 unlock >> 인증 후 비밀번호 변경
    // 3.4 비빌번호 찾기 >> 인증 후 비밀번호 변경
    // 3.5 비밀번호 fail count 초기화 하기 >> 인증 후 비밀번호 변경

    // 5. 삭제하기
    @Transactional(rollbackFor = {Exception.class})
    public void userWithdrawal(){
        User user = CommonUtil.getLoginUser()
                .orElseThrow(() -> new AuthException("로그인 정보가 없습니다."));
        user.deleteUser();
        userRepository.save(user);
    }

    /**
     * 로그인 성공 후 유저 정보 업데이트
     * @param event
     */
    @EventListener
    public void handleUserLoggedInEvent(UserLoggedInEvent event) {
        User user = event.getUser();
        user.setPwdFailCount(0);
        user.setLastLoginAt(LocalDateTime.now());
        user.setPwdUnlockCode(null);
        userRepository.save(user);
        log.debug("로그인 성공 후 유저 정보 업데이트 완료");
    }

}
