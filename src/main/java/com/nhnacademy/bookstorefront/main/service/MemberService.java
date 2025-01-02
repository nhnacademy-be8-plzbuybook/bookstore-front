package com.nhnacademy.bookstorefront.main.service;

import com.nhnacademy.bookstorefront.main.dto.Member.MemberCreateRequestDto;
import com.nhnacademy.bookstorefront.main.dto.Member.MemberCreateResponseDto;
import org.springframework.http.ResponseEntity;

public interface MemberService {
    MemberCreateResponseDto createMember(MemberCreateRequestDto memberCreateRequestDto);
}
