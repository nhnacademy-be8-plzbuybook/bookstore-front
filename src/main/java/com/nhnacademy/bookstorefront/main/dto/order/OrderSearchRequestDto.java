package com.nhnacademy.bookstorefront.main.dto.order;

import com.nhnacademy.bookstorefront.main.enums.OrderStatus;
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
    private OrderStatus orderStatus;
    @Nullable
    private String orderNumber;

    public OrderSearchRequestDto(@Nullable String memberId,
                                 @Nullable String productName,
                                 @Nullable LocalDate orderedAt,
                                 @Nullable OrderStatus orderStatus,
                                 @Nullable String orderNumber
    ) {
        this.memberId = memberId;
        this.productName = productName;
        this.orderedAt = orderedAt;
        this.orderStatus = orderStatus;
        this.orderNumber = orderNumber;
    }
}
