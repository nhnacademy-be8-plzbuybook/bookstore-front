package com.nhnacademy.bookstorefront.main.dto.coupon;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MemberCouponResponseDto(
        @Min(0)
        @NotNull
        Long memberCouponId, // 회원쿠폰 ID
        @Min(0)
        @NotNull
        Long memberId, // 회원 ID
        @NotNull
        CouponResponseDto coupon // 반환될 쿠폰관련 정보들
) {
    public record CouponResponseDto(
            Long couponId, // 쿠폰 ID
            String code, // 쿠폰 코드
            Status status, // 쿠폰 상태
            LocalDateTime issuedAt, // 발급일
            LocalDateTime expiredAt, // 만료일
            Long couponPolicyId, // 쿠폰정책 ID
            String name, // 쿠폰정책 이름
            String saleType, // 할인타입(고정, 비율)
            BigDecimal minimumAmount, // 적용을 위한 최소 금액
            BigDecimal discountLimit, // 최대 할인 금액
            Integer discountRatio, // 할인비율(고정할인일경우 0)
            boolean isStackable, // 중복 사용여부
            String couponScope, // 쿠폰 적용 범위
            boolean couponActive // 쿠폰정책 유효 여부
    ) {

    }
}
