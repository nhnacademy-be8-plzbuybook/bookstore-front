package com.nhnacademy.bookstorefront.main.service;

import com.nhnacademy.bookstorefront.main.dto.LoginRequestDto;
import com.nhnacademy.bookstorefront.main.dto.MemberDto;
import com.nhnacademy.bookstorefront.main.dto.auth.LoginResponseDto;

public interface AuthenticationService {
    LoginResponseDto processLogin(LoginRequestDto loginRequest);
    MemberDto processOauthLogin(String code);
}
