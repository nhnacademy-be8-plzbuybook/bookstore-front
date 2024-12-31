package com.nhnacademy.bookstorefront.main.dto.order.orderRequests;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class OrderProductAppliedCouponDto {
    private Long couponId;
    private BigDecimal discount;
}
