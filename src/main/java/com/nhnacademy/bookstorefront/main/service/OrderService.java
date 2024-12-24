package com.nhnacademy.bookstorefront.main.service;

import com.nhnacademy.bookstorefront.main.dto.OrderSaveResponseDto;
import com.nhnacademy.bookstorefront.main.dto.order.OrderSaveRequestDto;

public interface OrderService {
    OrderSaveResponseDto requestOrder(OrderSaveRequestDto orderSaveRequestDto);
}
