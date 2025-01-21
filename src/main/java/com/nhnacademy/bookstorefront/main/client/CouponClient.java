package com.nhnacademy.bookstorefront.main.client;

import com.nhnacademy.bookstorefront.main.dto.Member.MemberCouponGetResponseDto;
import com.nhnacademy.bookstorefront.main.dto.coupon.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "GATEWAY", contextId = "couponClient")
public interface CouponClient {

    // 쿠폰정책 등록 (쿠폰 정보 설정)
    @PostMapping("/api/coupon-policies")
    ResponseEntity<CouponPolicyResponseDto> createCouponPolicy(@RequestBody @Valid CouponPolicySaveRequestDto couponPolicySaveRequestDto);

    // 쿠폰정책 전체 조회
    @GetMapping("/api/coupon-policies")
    ResponseEntity<Page<CouponPolicyResponseDto>> findAllCouponPolicies(@RequestParam int page, @RequestParam int size);

    // 쿠폰정책 ID 검색
    @GetMapping("/api/coupon-policies/{id}")
    ResponseEntity<CouponPolicyResponseDto> findById(@PathVariable("id") @Min(0) Long couponPolicyId);

    // 쿠폰정책 이름 검색
    @GetMapping("/api/coupon-policies/search")
    ResponseEntity<CouponPolicyResponseDto> findByName(@RequestParam("name") @NotBlank String name);

    // 쿠폰 ID 로 쿠폰정책 검색
    @GetMapping("/api/coupon-policies/coupon/{coupon-id}")
    ResponseEntity<CouponPolicyResponseDto> findCouponPolicyByCouponId(@PathVariable("coupon-id") @Min(0) Long couponId);

    // 쿠폰정책에 쿠폰대상 추가
    @PostMapping("/api/coupon-policies/{policy-id}/targets")
    ResponseEntity<String> addCouponTargets(@PathVariable("policy-id") @Min(0) Long policyId, @RequestBody @Valid Long ctTargetId);

    // 특정 쿠폰정책에 속하는 쿠폰대상 목록 조회
    @GetMapping("/api/coupon-targets")
    ResponseEntity<Page<CouponTargetGetResponseDto>> getCouponTargetsByPolicy(@RequestParam("policy-id") @Min(0) Long policyId,
                                                                              @RequestParam(defaultValue = "0") @Min(0) int page,
                                                                              @RequestParam(defaultValue = "10") @Min(1) int pageSize);

    // 쿠폰생성
    @PostMapping("/api/coupons")
    ResponseEntity<CouponCreateResponseDto> createCoupon(@RequestBody @Valid CouponCreateRequestDto createRequest);

    // 쿠폰 전체 목록 조회
    @GetMapping("/api/coupons")
    ResponseEntity<Page<CouponResponseDto>> getAllCoupons(@RequestParam @Min(0) int page, @RequestParam @Min(1) int pageSize);

    // 특정 쿠폰정책에 속한 쿠폰 조회
    @GetMapping("/api/coupons/policies/{policy-id}")
    ResponseEntity<Page<CouponResponseDto>> getCouponsByPolicies(@PathVariable("policy-id") @Min(0) @NotNull Long policyId,
                                                                 @RequestParam(defaultValue = "0") @Min(0) int page,
                                                                 @RequestParam(defaultValue = "10") @Min(1) int pageSize);

    // 쿠폰 사용
    @PatchMapping("/api/coupons/{coupon-id}/use")
    ResponseEntity<String> useCoupon(@PathVariable("coupon-id") @Min(0) Long couponId);

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

    // 모든 회원쿠폰 정보 목록 조회
    @GetMapping("/api/member-coupons")
    ResponseEntity<Page<MemberCouponResponseDto>> getAllMemberCoupons(@RequestParam @NotNull @Min(0) int page, @RequestParam @NotNull @Min(1) int pageSize);

    // 쿠폰 ID로 회원쿠폰 조회
    @GetMapping("/api/member-coupons/{coupon-id}")
    ResponseEntity<Page<MemberCouponResponseDto>> getMemberCouponsByCouponId(@PathVariable("coupon-id") Long couponId,
                                                                             @RequestParam(defaultValue = "0") @Min(0) int page,
                                                                             @RequestParam(defaultValue = "10") @Min(1) int pageSize);

    // 회원이 사용가능한 쿠폰 목록 조회
    @GetMapping("/api/member-coupons/member/{member-id}/unused")
    ResponseEntity<Page<MemberCouponGetResponseDto>> getUnusedMemberCouponsByMemberId(@PathVariable("member-id") Long memberId, Pageable pageable);

    // 주문금액 할인 계산
    @PostMapping("/api/member-coupons/member/{coupon-id}/calculate")
    ResponseEntity<CouponCalculationResponseDto> applyOrderProductCoupon(@PathVariable("coupon-id") Long couponId,
                                                                         @RequestBody @Valid CouponCalculationRequestDto calculationRequestDto);

}
