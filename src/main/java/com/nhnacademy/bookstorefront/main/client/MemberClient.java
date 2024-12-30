package com.nhnacademy.bookstorefront.main.client;

import com.nhnacademy.bookstorefront.main.dto.Member.MemberAddressRequestDto;
import com.nhnacademy.bookstorefront.main.dto.Member.MemberAddressResponseDto;
import com.nhnacademy.bookstorefront.main.dto.Member.MemberModifyRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "GATEWAY", contextId = "memberClient")
public interface MemberClient {
    //회원 주소 등록
    @PostMapping("/api/members/address")
    ResponseEntity<MemberAddressResponseDto> createAddress(@RequestBody MemberAddressRequestDto addressRequestDto);

    //회원 주소 목록
    @GetMapping("/api/members/address")
    List<MemberAddressResponseDto> getAddressListByMemberEmail();

    //회원 주소 삭제
    @DeleteMapping("/api/members/address/{address_id}")
    ResponseEntity<Void> deleteAddress(@PathVariable Long address_id);
}
