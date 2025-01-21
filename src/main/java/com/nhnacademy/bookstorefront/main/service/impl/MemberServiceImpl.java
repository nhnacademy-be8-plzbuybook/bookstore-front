package com.nhnacademy.bookstorefront.main.service.impl;

import com.nhnacademy.bookstorefront.main.client.AuthenticationClient;
import com.nhnacademy.bookstorefront.main.client.MemberClient;
import com.nhnacademy.bookstorefront.main.dto.Member.MemberCreateRequestDto;
import com.nhnacademy.bookstorefront.main.dto.Member.MemberCreateResponseDto;
import com.nhnacademy.bookstorefront.main.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberClient memberClient;
    private final AuthenticationClient authenticationClient;

    @Override
    public MemberCreateResponseDto createMember(MemberCreateRequestDto memberCreateRequestDto) {
        ResponseEntity<MemberCreateResponseDto> responseEntity = authenticationClient.createMember(memberCreateRequestDto);
        return responseEntity.getBody();
    }

    @Override
    public Long getMemberIdByEmail(String email) {
        ResponseEntity<Long> responseEntity = memberClient.getMemberIdByMemberEmail(email);
        return responseEntity.getBody();
    }
}
