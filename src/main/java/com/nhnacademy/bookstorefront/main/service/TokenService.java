package com.nhnacademy.bookstorefront.main.service;

import com.nhnacademy.bookstorefront.main.dto.MemberDto;

public interface TokenService {
    String issueAccessToken(MemberDto memberDto);
}
