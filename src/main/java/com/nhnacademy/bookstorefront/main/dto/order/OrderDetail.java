package com.nhnacademy.bookstorefront.main.dto.order;

import com.nhnacademy.bookstorefront.main.dto.order.orderRequests.OrderDeliveryAddressDto;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderDetail {
    private List<OrderProductDto> orderProducts;
    private OrderDeliveryAddressDto orderDeliveryAddress;
    private PaymentDto payment;

    public OrderDetail(List<OrderProductDto> orderProducts, OrderDeliveryAddressDto orderDeliveryAddress, PaymentDto payment) {
        this.orderProducts = orderProducts;
        this.orderDeliveryAddress = orderDeliveryAddress;
        this.payment = payment;
    }
}