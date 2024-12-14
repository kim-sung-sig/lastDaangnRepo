package com.demo.daangn.app.service.user;

import java.time.LocalDateTime;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.daangn.app.common.exception.CustomBusinessException;
import com.demo.daangn.app.common.exception.CustomSystemException;
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

        if (!CommonUtil.isNotBlank(username, password, email, nickName, token)) { // valid 에서 막을테지만 혹시 몰라..
            throw new CustomBusinessException("필수값이 누락되었습니다.");
        }

        // 1. username 중복 체크
        userRepository.findByUsername(username).ifPresent(user -> {
            throw new CustomBusinessException("이미 존재하는 아이디입니다.");
        });
        // 2. 이메일 검증 체크 -> email regex + email DB 체크
        boolean emailCheck = emailService.verifyEmail(email, token);
        if (!emailCheck) {
            throw new CustomBusinessException("이메일 인증에 실패하였습니다. 잠시후 다시 시도해 주세요.");
        }

        // 로직 시작
        try {
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
        } catch (Exception e) {
            log.error("***********************************");
            log.error("** fail userSignUp");
            log.error("** LOG LINE : {}", e.getStackTrace()[0].getLineNumber());
            log.error("** LOG MSG : {}", e.getMessage());
            throw new CustomSystemException("회원가입 실패", e);
        }
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
                .orElseThrow(() -> new CustomSystemException("로그인 정보가 없습니다."));
        user.deleteUser();
        userRepository.save(user);
    }

    /**
     * 로그인 성공 후 유저 정보 업데이트
     * @param event
     */
    @EventListener
    public void handleUserLoggedInEvent(UserLoggedInEvent event) {
        try {
            User user = event.getUser();
            user.setPwdFailCount(0);
            user.setLastLoginAt(LocalDateTime.now());
            user.setPwdUnlockCode(null);
            userRepository.save(user);
            log.debug("로그인 성공 후 유저 정보 업데이트 완료");
        } catch (Exception e) {
            log.error("***********************************");
            log.error("** fail handleUserLoggedInEvent");
            log.error("** LOG LINE : {}", e.getStackTrace()[0].getLineNumber());
            log.error("** LOG MSG : {}", e.getMessage());
        }
    }

}
