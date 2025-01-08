package com.nhnacademy.bookstorefront.main.dto.coupon;

public record CouponTargetResponseDto(
        Long couponTargetId, // 쿠폰대상 ID
        Long couponPolicyId, // 쿠폰정책 ID
        Long ctTargetId // 쿠폰대상이 참조하고있는 대상의 ID
) {
}
