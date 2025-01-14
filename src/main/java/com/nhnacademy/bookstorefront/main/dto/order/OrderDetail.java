package com.nhnacademy.bookstorefront.main.dto.order;

import com.nhnacademy.bookstorefront.main.dto.order.orderRequests.OrderDeliveryAddressDto;
import com.nhnacademy.bookstorefront.main.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderDetail {
    private String orderId;
    private List<OrderProductDto> orderProducts;
    private OrderDeliveryAddressDto orderDeliveryAddress;
    private OrderDeliveryDto orderDelivery;
    private PaymentDto payment;
    private BigDecimal deliveryFee;
    private BigDecimal orderPrice;
    private LocalDateTime orderedAt;
    private LocalDate deliveryWishDate;
    private OrderStatus status;
    private int usedPoint;
    private BigDecimal couponDiscount;
    private String orderNumber;

}