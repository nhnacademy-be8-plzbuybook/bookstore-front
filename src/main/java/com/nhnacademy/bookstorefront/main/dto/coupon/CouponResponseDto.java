package com.nhnacademy.bookstorefront.main.dto.coupon;

import java.time.LocalDateTime;

public record CouponResponseDto(
        Long id,
        String code,
        Status status,
        LocalDateTime issuedAt,
        LocalDateTime expiredAt,
        Long policyId
) {
}
