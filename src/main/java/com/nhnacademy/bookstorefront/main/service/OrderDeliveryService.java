package com.nhnacademy.bookstorefront.main.service;

import com.nhnacademy.bookstorefront.main.dto.OrderDeliveryRegisterRequestDto;

public interface OrderDeliveryService {
    void registerOrderDelivery(String orderId, OrderDeliveryRegisterRequestDto registerRequest);
}
