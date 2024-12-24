package com.nhnacademy.bookstorefront.main.service;

import com.nhnacademy.bookstorefront.main.dto.LoginRequestDto;
import com.nhnacademy.bookstorefront.main.dto.MemberDto;

public interface AuthenticationService {
    MemberDto processLogin(LoginRequestDto loginRequest);
    MemberDto processOauthLogin(String code);
}
