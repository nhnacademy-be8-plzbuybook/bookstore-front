package com.nhnacademy.bookstorefront.main.service.impl;

import com.nhnacademy.bookstorefront.common.exception.LoginFailException;
import com.nhnacademy.bookstorefront.common.exception.OauthMemberNotRegisteredException;
import com.nhnacademy.bookstorefront.main.client.AuthenticationClient;
import com.nhnacademy.bookstorefront.main.dto.LoginRequestDto;
import com.nhnacademy.bookstorefront.main.dto.auth.LoginResponseDto;
import com.nhnacademy.bookstorefront.main.dto.auth.OauthLoginResponseDto;
import com.nhnacademy.bookstorefront.main.dto.mypage.MyPageDto;
import com.nhnacademy.bookstorefront.main.service.AuthenticationService;
import feign.FeignException;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationClient authenticationClient;

    @Override
    public LoginResponseDto processLogin(LoginRequestDto loginRequest) {
        try {
            ResponseEntity<LoginResponseDto> responseEntity = authenticationClient.login(loginRequest);
            LoginResponseDto loginResponse =  responseEntity.getBody();


            if (loginResponse == null || loginResponse.memberStateName() == null) {
                throw new LoginFailException("회원 상태 정보가 유효하지 않습니다.");
            }

            if ("WITHDRAWAL".equals(loginResponse.memberStateName())) {
                // 탈퇴한 회원의 경우
                throw new LoginFailException("이미 탈퇴한 회원입니다.");
            }

            return loginResponse;

        } catch (LoginFailException e) {
            log.error("Login failed: {}", e.getMessage());
            throw e;
        } catch (FeignException e) {
            log.error("Feign Client error: {}", e.getMessage());
            throw new LoginFailException("로그인 중 오류가 발생했습니다.");
        } catch (Exception e) {
            log.error("Unexpected login error: {}", e.getMessage());
            throw new LoginFailException("로그인 중 오류가 발생했습니다.");
        }
    }

    @Override
    public OauthLoginResponseDto processOauthLogin(String code) {
        try {
            ResponseEntity<OauthLoginResponseDto> response = authenticationClient.getEmailFromOauthUser(code);
            OauthLoginResponseDto oauthLoginResponseDto = response.getBody();

            if (oauthLoginResponseDto == null) {
                throw new LoginFailException("회원정보 불러오기가 실패했습니다.");
            }

            // 등록 안된 회원이면 회원가입 페이지로 이동
            if (!oauthLoginResponseDto.isRegistered()) {
                String email = oauthLoginResponseDto.email();
                throw new OauthMemberNotRegisteredException(email);
            }

            if ("WITHDRAWAL".equals(oauthLoginResponseDto.memberStateName())) {
                throw new LoginFailException("이미 탈퇴한 회원입니다.");
            }

            return oauthLoginResponseDto;

        } catch (FeignException e) {
            log.error("login error: {}", e.getMessage());
            throw new LoginFailException("로그인 중 오류가 발생했습니다.");
        }
    }

    @Override
    public MyPageDto getMyPage() {
        try{
            ResponseEntity<MyPageDto> response = authenticationClient.getMemberMyPage();
            return response.getBody();
        }catch(FeignException  e){
            throw new RuntimeException("마이페이지 조회 중 오류 발생!");
        }
    }
}
