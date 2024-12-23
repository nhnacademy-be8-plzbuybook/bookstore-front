package com.nhnacademy.bookstorefront.main.service.impl;

import com.nhnacademy.bookstorefront.common.exception.LoginFailException;
import com.nhnacademy.bookstorefront.main.client.AuthenticationClient;
import com.nhnacademy.bookstorefront.main.dto.LoginRequestDto;
import com.nhnacademy.bookstorefront.main.dto.MemberDto;
import com.nhnacademy.bookstorefront.main.service.AuthenticationService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationClient authenticationClient;

    @Override
    public MemberDto processLogin(LoginRequestDto loginRequest) {
        try {
            ResponseEntity<MemberDto> responseEntity = authenticationClient.login(loginRequest);
            return responseEntity.getBody();
        } catch (FeignException.NotFound | FeignException.Unauthorized e) {
            throw new LoginFailException("잘못된 아이디 또는 비밀번호입니다.");
        } catch (RuntimeException e) {
            throw new LoginFailException("다시 로그인 해주세요.");
        }
    }
}
