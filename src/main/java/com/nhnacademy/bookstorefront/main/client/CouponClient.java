package com.nhnacademy.bookstorefront.main.client;

import com.nhnacademy.bookstorefront.main.dto.coupon.*;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "GATEWAY", contextId = "couponClient")
public interface CouponClient {

    // 특정 쿠폰을 주문 상품에 적용하여 (쿠폰서버에서) 할인 계산
    @PostMapping("/api/member-coupons/member/{member-id}/coupon/{coupon-id}/calculate")
    ResponseEntity<CouponCalculationResponseDto> applyOrderProductCoupon(@PathVariable("member-id") Long memberId, @PathVariable("coupon-id") Long couponId, @RequestBody @Valid CouponCalculationRequestDto calculationRequestDto);

    // 쿠폰정책 등록 (쿠폰 정보 설정)
    @PostMapping("/api/coupon-policies")
    ResponseEntity<CouponPolicyResponseDto> createCouponPolicy(@RequestBody @Valid CouponPolicySaveRequestDto couponPolicySaveRequestDto);

    // 쿠폰 ID에 해당하는 쿠폰 이력 목록 조회
    @GetMapping("/api/coupon-histories/{coupon-id}")
    ResponseEntity<Page<CouponHistoryResponseDto>> getHistoryByCouponId(@PathVariable("coupon-id") Long couponId,
                                                                        @RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int pageSize);

    // 특정 기간의 쿠폰 이력 목록 조회
    @GetMapping("/api/coupon-histories/period")
    ResponseEntity<Page<CouponHistoryResponseDto>> getHistoryDate(@RequestParam("start-date") String startDate,
                                                                         @RequestParam("end-date") String endDate,
                                                                         @RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "10") int pageSize);

    // 회원에게 쿠폰 발급
    @PostMapping("/api/member-coupons")
    ResponseEntity<MemberCouponResponseDto> createMemberCoupon(@RequestBody @Valid MemberCouponCreateRequestDto memberCouponCreateRequestDto);


}
