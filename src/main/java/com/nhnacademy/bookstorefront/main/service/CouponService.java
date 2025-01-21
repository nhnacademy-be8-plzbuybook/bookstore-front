package com.nhnacademy.bookstorefront.main.service;

import com.nhnacademy.bookstorefront.main.dto.Member.MemberCouponGetResponseDto;
import com.nhnacademy.bookstorefront.main.dto.coupon.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface CouponService {
    CouponPolicyResponseDto createCouponPolicy(CouponPolicySaveRequestDto couponPolicySaveRequestDto);

    Page<CouponPolicyResponseDto> findAllCouponPolicies(@RequestParam int page, @RequestParam int size);

    CouponPolicyResponseDto findById(@PathVariable("id") @Min(0) Long couponPolicyId);

    CouponPolicyResponseDto findByName(@RequestParam("name") @NotBlank String name);

    CouponPolicyResponseDto findCouponPolicyByCouponId(@PathVariable("coupon-id") @Min(0) Long couponId);

    String addCouponTargets(@PathVariable("policy-id") @Min(0) Long policyId, @RequestBody @Valid Long ctTargetId);

    Page<CouponTargetGetResponseDto> getCouponTargetsByPolicy(@RequestParam("policy-id") @Min(0) Long policyId,
                                                              @RequestParam(defaultValue = "0") @Min(0) int page,
                                                              @RequestParam(defaultValue = "10") @Min(1) int pageSize);

    CouponCreateResponseDto createCoupon(CouponCreateRequestDto createRequest);

    Page<CouponResponseDto> getAllCoupons(int page, int pageSize);

    Page<CouponResponseDto> getCouponsByPolicies(@PathVariable("policy-id") @Min(0) @NotNull Long policyId,
                                                 @RequestParam(defaultValue = "0") @Min(0) int page,
                                                 @RequestParam(defaultValue = "10") @Min(1) int pageSize);

    String useCoupon(@PathVariable("coupon-id") @Min(0) Long couponId);

    Page<CouponHistoryResponseDto> getHistoryByCouponId(Long couponId, int page, int pageSize);

    Page<CouponHistoryResponseDto> getHistoryDate(String startDate, String endDate, int page, int pageSize);

    MemberCouponResponseDto createMemberCoupon(@RequestBody @Valid MemberCouponCreateRequestDto memberCouponCreateRequestDto);

    Page<MemberCouponResponseDto> getAllMemberCoupons(int page, int pageSize);

    Page<MemberCouponResponseDto> getMemberCouponsByCouponId(Long couponId, int page, int pageSize);

    Page<MemberCouponGetResponseDto> getUnusedMemberCouponsByMemberId(Long memberId, Pageable pageable);

    CouponCalculationResponseDto applyOrderProductCoupon(Long couponId, CouponCalculationRequestDto calculationRequestDto, HttpServletRequest request);
}
