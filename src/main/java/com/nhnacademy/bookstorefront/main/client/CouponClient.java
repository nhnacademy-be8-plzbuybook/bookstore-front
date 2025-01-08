package com.nhnacademy.bookstorefront.main.client;

import com.nhnacademy.bookstorefront.main.dto.coupon.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@FeignClient(name = "GATEWAY", contextId = "couponClient")
public interface CouponClient {

    // 쿠폰정책 등록 (쿠폰 정보 설정)
    @PostMapping("/api/coupon-policies")
    ResponseEntity<CouponPolicyResponseDto> createCouponPolicy(@RequestBody @Valid CouponPolicySaveRequestDto couponPolicySaveRequestDto);

    // 활성화된 쿠폰정책 조회
    @GetMapping("/api/coupon-policies")
    ResponseEntity<Page<CouponPolicyResponseDto>> findActiveCouponPolicies(@RequestParam(defaultValue = "true") boolean couponActive,
                                                                           @RequestParam(defaultValue = "0") @Min(0) int page,
                                                                           @RequestParam(defaultValue = "10") @Min(1) int pageSize);

    // 쿠폰타켓 생성
    @PostMapping("/api/coupon-targets")
    ResponseEntity<CouponTargetResponseDto> createCouponTarget(@RequestBody @Valid CouponTargetSaveRequestDto couponTargetSaveRequestDto);

    // 쿠폰생성
    @PostMapping("/api/coupons")
    ResponseEntity<CouponCreateResponseDto> createCoupon(@RequestBody @Valid CouponCreateRequestDto createRequest);

    // 쿠폰 전체 목록 조회
    @GetMapping("api/coupons")
    ResponseEntity<Page<CouponResponseDto>> getAllCoupons(@RequestParam @Min(0) int page, @RequestParam @Min(1)int pageSize);

    // 현재 시간을 기준으로 사용가능한 쿠폰 목록 조회
    @GetMapping("/api/coupons/active")
    ResponseEntity<Page<CouponResponseDto>> getActiveCoupons(@RequestParam("currentDateTime") @NotNull LocalDateTime currentDateTime,
                                                             @RequestParam(defaultValue = "0") @Min(0) int page,
                                                             @RequestParam(defaultValue = "10") @Min(1) int pageSize);

    // 특정 쿠폰을 주문 상품에 적용하여 (쿠폰서버에서) 할인 계산
    @PostMapping("/api/member-coupons/member/{member-id}/coupon/{coupon-id}/calculate")
    ResponseEntity<CouponCalculationResponseDto> applyOrderProductCoupon(@PathVariable("member-id") Long memberId, @PathVariable("coupon-id") Long couponId, @RequestBody @Valid CouponCalculationRequestDto calculationRequestDto);


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

    // 쿠폰 ID로 회원쿠폰 조회
    @GetMapping("/api/member-coupons/{coupon-id}")
    ResponseEntity<Page<MemberCouponResponseDto>> getMemberCouponsByCouponId(@PathVariable("coupon-id") Long couponId,
                                                                             @RequestParam(defaultValue = "0") @Min(0) int page,
                                                                             @RequestParam(defaultValue = "10") @Min(1) int pageSize);


}
