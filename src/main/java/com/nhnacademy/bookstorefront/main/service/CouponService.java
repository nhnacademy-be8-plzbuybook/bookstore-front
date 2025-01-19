package com.nhnacademy.bookstorefront.main.service;

import com.nhnacademy.bookstorefront.main.dto.Member.MemberCouponGetResponseDto;
import com.nhnacademy.bookstorefront.main.dto.coupon.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

public interface CouponService {
    CouponPolicyResponseDto createCouponPolicy(CouponPolicySaveRequestDto couponPolicySaveRequestDto);

    Page<CouponPolicyResponseDto> findActiveCouponPolicies(boolean active, int page, int pageSize);

    CouponTargetResponseDto createCouponTarget(CouponTargetSaveRequestDto couponTargetSaveRequestDto);

    CouponCreateResponseDto createCoupon(CouponCreateRequestDto createRequest);

    Page<CouponResponseDto> getAllCoupons(int page, int pageSize);

    Page<MemberCouponResponseDto> getAllMemberCoupons(int page, int pageSize);

    CouponCalculationResponseDto applyOrderProductCoupon(Long couponId, CouponCalculationRequestDto calculationRequestDto, HttpServletRequest request);

    Page<MemberCouponGetResponseDto> getUnusedMemberCouponsByMemberId(Long memberId, Pageable pageable);

    Page<CouponHistoryResponseDto> getHistoryByCouponId(Long couponId, int page, int pageSize);

    Page<CouponHistoryResponseDto> getHistoryDate(String startDate, String endDate, int page, int pageSize);

    MemberCouponResponseDto createMemberCoupon(@RequestBody @Valid MemberCouponCreateRequestDto memberCouponCreateRequestDto);

    Page<MemberCouponResponseDto> getMemberCouponsByCouponId(Long couponId, int page, int pageSize);
}
