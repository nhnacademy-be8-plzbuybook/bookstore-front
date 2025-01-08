package com.nhnacademy.bookstorefront.main.dto.coupon;

import java.time.LocalDateTime;

public record CouponHistoryResponseDto(
        Long historyId,
        String status,
        LocalDateTime changeDate,
        String reason,
        Long couponId
) {
}
