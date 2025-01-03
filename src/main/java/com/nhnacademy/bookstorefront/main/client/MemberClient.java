package com.nhnacademy.bookstorefront.main.client;

import com.nhnacademy.bookstorefront.main.dto.Member.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    //회원 주소 수정
    @PostMapping("/api/members/address/{address_id}")
    MemberAddressResponseDto updateAddress(@PathVariable Long address_id, @RequestBody MemberAddressRequestDto addressRequestDto);

    //회원 쿠폰 리스트 조회
    @GetMapping("/api/coupons/member-coupons/member/{memberId}")
    Page<MemberCouponGetResponseDto> getMemberCouponsByMemberId(@PathVariable("memberId") Long memberId, Pageable pageable);

    //전체 회원 리스트 조회(관리자 페이지)
    @GetMapping("/api/members")
    ResponseEntity<Page<MemberSearchResponseDto>> getMembers(@SpringQueryMap MemberSearchRequestDto memberSearchRequestDto);

    //전체 상태 호출(관리자 페이지)
    @GetMapping("/api/members/status/all")
    List<MemberStatus> getAllMemberStatus();


    @PostMapping("/api/member-selling-books/like/{sellingBookId}")
    ResponseEntity<Long> toggleLike(@PathVariable("sellingBookId") Long sellingBookId);

    // 포인트 내역 조회
    @GetMapping("/api/members/{memberId}/points")
    List<MemberPointResponseDto> getMemberPoints(@PathVariable("memberId") Long memberId);
}




