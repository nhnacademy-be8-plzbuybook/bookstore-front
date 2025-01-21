package com.nhnacademy.bookstorefront.main.service;

import com.nhnacademy.bookstorefront.main.dto.Member.MemberCouponGetResponseDto;
import com.nhnacademy.bookstorefront.main.dto.coupon.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface CouponService {
    CouponPolicyResponseDto createCouponPolicy(CouponPolicySaveRequestDto couponPolicySaveRequestDto);

    Page<CouponPolicyResponseDto> findAllCouponPolicies(@RequestParam int page, @RequestParam int size);

    String addCouponTargets(@PathVariable("policy-id") @Min(0) Long policyId, @RequestBody @Valid Long ctTargetId);

    CouponCreateResponseDto createCoupon(CouponCreateRequestDto createRequest);

    Page<CouponResponseDto> getAllCoupons(int page, int pageSize);

    Page<CouponHistoryResponseDto> getHistoryByCouponId(Long couponId, int page, int pageSize);

    MemberCouponResponseDto createMemberCoupon(@RequestBody @Valid MemberCouponCreateRequestDto memberCouponCreateRequestDto);

    Page<MemberCouponResponseDto> getAllMemberCoupons(int page, int pageSize);

    Page<MemberCouponGetResponseDto> getUnusedMemberCouponsByMemberId(Long memberId, Pageable pageable);

    CouponCalculationResponseDto applyOrderProductCoupon(Long couponId, CouponCalculationRequestDto calculationRequestDto, HttpServletRequest request);
}
