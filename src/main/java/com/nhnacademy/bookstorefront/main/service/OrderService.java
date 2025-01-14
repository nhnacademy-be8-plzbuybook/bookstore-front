package com.nhnacademy.bookstorefront.main.service;

import com.nhnacademy.bookstorefront.main.dto.OrderCancelRequestDto;
import com.nhnacademy.bookstorefront.main.dto.OrderProductCancelRequestDto;
import com.nhnacademy.bookstorefront.main.dto.order.*;
import com.nhnacademy.bookstorefront.main.dto.order.orderRequests.MemberOrderRequestDto;
import com.nhnacademy.bookstorefront.main.dto.order.orderRequests.NonMemberOrderRequestDto;
import com.nhnacademy.bookstorefront.main.enums.OrderStatus;
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

    List<OrderStatus> getOrderStatuses();

    void cancelOrderProduct(String orderProductId, OrderProductCancelRequestDto cancelRequest);

    void cancelOrder(String orderId, OrderCancelRequestDto cancelRequest);

    void modifyOrderStatus(String orderId, StatusDto status);

    void completeOrderDelivery(String orderId, Long deliveryId);

    void requestReturnOrder(String orderId, OrderReturnRequestDto returnRequest);

    void completeReturningOrder(String orderId);

    Page<OrderReturnDto> getOrderReturns(String trackingNumber, Pageable pageable);

}
