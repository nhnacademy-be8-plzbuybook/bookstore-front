package com.nhnacademy.bookstorefront.main.dto.order.orderRequests;

import com.nhnacademy.bookstorefront.main.dto.order.OrderType;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class MemberOrderRequestDto extends OrderRequestDto{
    @Setter
    private String memberEmail;

    public MemberOrderRequestDto(@Nullable LocalDate deliveryWishDate, Integer usedPoint,
                                 OrderDeliveryAddressDto orderDeliveryAddressDto, List<OrderProductRequestDto> orderProducts) {
        super(OrderType.MEMBER_ORDER, deliveryWishDate, usedPoint, orderProducts, orderDeliveryAddressDto);
    }
}
