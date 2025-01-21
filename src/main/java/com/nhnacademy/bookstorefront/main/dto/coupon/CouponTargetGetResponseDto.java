package com.nhnacademy.bookstorefront.main.dto.coupon;

public record CouponTargetGetResponseDto(
        Long couponTargetId, // 쿠폰대상 ID
        Long ctTargetId, // 대상 도메인의 ID
        Long couponPolicyId, // 쿠폰정책 ID
        String scope // 쿠폰정책 ID 에 해당하는 범위
) {
}
