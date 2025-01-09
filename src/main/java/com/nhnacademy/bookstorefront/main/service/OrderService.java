package com.nhnacademy.bookstorefront.main.service;

import com.nhnacademy.bookstorefront.main.dto.order.*;
import com.nhnacademy.bookstorefront.main.dto.order.orderRequests.MemberOrderRequestDto;
import com.nhnacademy.bookstorefront.main.dto.order.orderRequests.NonMemberOrderRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    OrderSaveResponseDto requestNonMemberOrder(NonMemberOrderRequestDto orderSaveRequestDto);
    OrderSaveResponseDto requestMemberOrder(MemberOrderRequestDto orderSaveRequestDto);
    String completeOrder(String orderId);
    Page<OrderDto> getMemberOrders(OrderSearchRequestDto searchRequest, Pageable pageable);
    Page<OrderDto> getAllOrders(OrderSearchRequestDto searchRequest, Pageable pageable);
    OrderDetail getOrderDetail(String orderId);
    String getNonMemberOrderId(NonMemberOrderDetailAccessRequestDto accessRequest);
    List<String> getOrderStatuses();
}
