package com.nhnacademy.bookstorefront.main.service;

import com.nhnacademy.bookstorefront.main.dto.LoginRequestDto;
import com.nhnacademy.bookstorefront.main.dto.auth.LoginResponseDto;
import com.nhnacademy.bookstorefront.main.dto.auth.OauthLoginResponseDto;

public interface AuthenticationService {
    LoginResponseDto processLogin(LoginRequestDto loginRequest);
    OauthLoginResponseDto processOauthLogin(String code);
}
