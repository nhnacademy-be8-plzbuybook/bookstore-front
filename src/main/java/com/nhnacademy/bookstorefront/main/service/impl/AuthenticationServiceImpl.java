package com.nhnacademy.bookstorefront.main.service.impl;

import com.nhnacademy.bookstorefront.common.exception.LoginFailException;
import com.nhnacademy.bookstorefront.common.exception.OauthMemberNotRegisteredException;
import com.nhnacademy.bookstorefront.main.client.AuthenticationClient;
import com.nhnacademy.bookstorefront.main.dto.LoginRequestDto;
import com.nhnacademy.bookstorefront.main.dto.auth.LoginResponseDto;
import com.nhnacademy.bookstorefront.main.dto.auth.OauthLoginResponseDto;
import com.nhnacademy.bookstorefront.main.service.AuthenticationService;
import com.nhnacademy.bookstorefront.main.service.TokenService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationClient authenticationClient;

    @Override
    public LoginResponseDto processLogin(LoginRequestDto loginRequest) {
        try {
            ResponseEntity<LoginResponseDto> responseEntity = authenticationClient.login(loginRequest);
            return responseEntity.getBody();
        } catch (FeignException.NotFound | FeignException.Unauthorized e) {
            throw new LoginFailException("잘못된 아이디 또는 비밀번호입니다.");
        } catch (RuntimeException e) {
            throw new LoginFailException("로그인 중 오류 발생했습니다.");
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

            return oauthLoginResponseDto;

        } catch (FeignException e) {
            throw new LoginFailException("로그인 중 오류가 발생했습니다.");
        }
    }
}
