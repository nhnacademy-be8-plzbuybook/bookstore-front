package com.nhnacademy.bookstorefront.main.client;

import com.nhnacademy.bookstorefront.main.dto.BookDetailResponseDto;
import com.nhnacademy.bookstorefront.main.dto.Member.*;
import com.nhnacademy.bookstorefront.main.dto.point.MemberPointUseRequestDto;
import com.nhnacademy.bookstorefront.main.dto.point.PointConditionRequestDto;
import com.nhnacademy.bookstorefront.main.dto.point.PointConditionResponseDto;
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
    @GetMapping("/api/member-coupons/member/{member-id}")
    Page<MemberCouponGetResponseDto> getMemberCouponsByMemberId(@PathVariable("member-id") Long memberId, Pageable pageable);

    //전체 회원 리스트 조회(관리자 페이지)
    @GetMapping("/api/members")
    ResponseEntity<Page<MemberSearchResponseDto>> getMembers(@SpringQueryMap MemberSearchRequestDto memberSearchRequestDto);

    //전체 상태 호출(관리자 페이지)
    @GetMapping("/api/members/status/all")
    List<MemberStatus> getAllMemberStatus();

    //전체 등급 호출(관리자 페이지)
    @GetMapping("/api/members/grade/all")
    List<MemberGrade> getAllMemberGrade();


    @PostMapping("/api/member-selling-books/like/{sellingBookId}")
    ResponseEntity<Long> toggleLike(@PathVariable("sellingBookId") Long sellingBookId);


    //회원 수정(관리자 페이지)
    @PostMapping("/api/members/email")
    ResponseEntity<Void> updateMember(@RequestBody MemberModifyByAdminRequestDto memberModifyByAdminRequestDto);

    //회원이 좋아요 누른 책 호출(마이 페이지)
    @GetMapping("/api/members/{memberId}/liked-books")
    Page<BookDetailResponseDto> getLikedBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "16") int size,
            @PathVariable Long memberId
    );

    //이메일로 회원식별키 조회
    @GetMapping("/api/members/id")
    ResponseEntity<Long> getMemberIdByMemberEmail(@RequestParam String memberEmail);
}




