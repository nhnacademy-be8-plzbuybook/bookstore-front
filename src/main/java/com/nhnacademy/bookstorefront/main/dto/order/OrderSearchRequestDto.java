package com.nhnacademy.bookstorefront.main.dto.order;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
public class OrderSearchRequestDto {

    @Setter
    @Nullable
    private String memberId;
    @Nullable
    private String productName;
    @Nullable
    private LocalDate orderedAt;
    @Nullable
    private String orderStatus;

    public OrderSearchRequestDto(@Nullable String memberId, @Nullable String productName,
                                 @Nullable LocalDate orderedAt, @Nullable String orderStatus) {
        this.memberId = memberId;
        this.productName = productName;
        this.orderedAt = orderedAt;
        this.orderStatus = orderStatus;
    }
}
