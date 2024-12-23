package com.nhnacademy.bookstorefront.main.service.impl;

import com.nhnacademy.bookstorefront.common.exception.LoginFailException;
import com.nhnacademy.bookstorefront.main.client.AuthenticationClient;
import com.nhnacademy.bookstorefront.main.dto.AccessTokenResponseDto;
import com.nhnacademy.bookstorefront.main.dto.MemberDto;
import com.nhnacademy.bookstorefront.main.service.TokenService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenServiceImpl implements TokenService {
    private final AuthenticationClient authenticationClient;

    @Override
    public String issueAccessToken(MemberDto memberDto) {
        try {
            ResponseEntity<AccessTokenResponseDto> response = authenticationClient.issueAccessToken(memberDto);
            AccessTokenResponseDto tokenResponse = response.getBody();
            if (tokenResponse == null) {
                throw new LoginFailException("토큰 발급 실패, 다시 로그인 해주세요");
            }
            return tokenResponse.accessToken();

        } catch (FeignException e) {
            throw new LoginFailException("토큰 발급 실패, 다시 로그인 해주세요");
        } catch (RuntimeException e) {
            throw new LoginFailException("다시 로그인 해주세요.");
        }

    }
}
