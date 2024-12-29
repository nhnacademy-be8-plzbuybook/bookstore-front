package com.nhnacademy.bookstorefront.main.service;

import com.nhnacademy.bookstorefront.main.dto.OrderSaveResponseDto;
import com.nhnacademy.bookstorefront.main.dto.order.OrderDto;
import com.nhnacademy.bookstorefront.main.dto.order.OrderSaveRequestDto;
import com.nhnacademy.bookstorefront.main.dto.order.OrderSearchRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderSaveResponseDto requestOrder(OrderSaveRequestDto orderSaveRequestDto);
    String completeOrder(String orderId);
    Page<OrderDto> getOrders(OrderSearchRequestDto searchRequest, Pageable pageable);
}
