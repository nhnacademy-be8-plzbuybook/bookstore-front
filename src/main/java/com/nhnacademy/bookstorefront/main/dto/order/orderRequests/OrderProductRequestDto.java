package com.nhnacademy.bookstorefront.main.dto.order.orderRequests;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class OrderProductRequestDto {
    @NotNull
    private Long productId;

    @NotNull
    private BigDecimal price;

    @Min(1)
    @NotNull
    private Integer quantity;

    @Nullable
    private List<OrderProductAppliedCouponDto> appliedCoupons;

    @Nullable
    private OrderProductWrappingRequestDto wrapping;
}
