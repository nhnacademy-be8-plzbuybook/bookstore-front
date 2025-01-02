package com.nhnacademy.bookstorefront.main.service;

import com.nhnacademy.bookstorefront.main.dto.OrderSaveResponseDto;
import com.nhnacademy.bookstorefront.main.dto.order.OrderDto;
import com.nhnacademy.bookstorefront.main.dto.order.OrderSearchRequestDto;
import com.nhnacademy.bookstorefront.main.dto.order.orderRequests.MemberOrderRequestDto;
import com.nhnacademy.bookstorefront.main.dto.order.orderRequests.NonMemberOrderRequestDto;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
//    OrderSaveResponseDto requestNonMemberOrder(JSONObject orderSaveRequestDto);
    OrderSaveResponseDto requestNonMemberOrder(NonMemberOrderRequestDto orderSaveRequestDto);
    OrderSaveResponseDto requestMemberOrder(MemberOrderRequestDto orderSaveRequestDto);
    String completeOrder(String orderId);
    Page<OrderDto> getOrders(OrderSearchRequestDto searchRequest, Pageable pageable);
}
