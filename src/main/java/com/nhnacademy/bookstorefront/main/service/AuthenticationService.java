package com.nhnacademy.bookstorefront.main.service;

import com.nhnacademy.bookstorefront.main.dto.LoginRequestDto;
import com.nhnacademy.bookstorefront.main.dto.auth.LoginResponseDto;
import com.nhnacademy.bookstorefront.main.dto.auth.OauthLoginResponseDto;
import com.nhnacademy.bookstorefront.main.dto.mypage.MyPageDto;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService {
    boolean isLoggedIn(HttpServletRequest request);
    LoginResponseDto processLogin(LoginRequestDto loginRequest);
    OauthLoginResponseDto processOauthLogin(String code);
    MyPageDto getMyPage();
    String getEmailFromToken(HttpServletRequest request);
}
