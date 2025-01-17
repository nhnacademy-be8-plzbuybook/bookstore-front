package com.nhnacademy.bookstorefront.main.service;

import com.nhnacademy.bookstorefront.main.controller.order.OrderProductReturnDto;
import com.nhnacademy.bookstorefront.main.dto.order.*;
import com.nhnacademy.bookstorefront.main.dto.order.orderRequests.OrderRequestDto;
import com.nhnacademy.bookstorefront.main.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    OrderSaveResponseDto requestOrder(OrderRequestDto orderRequest);

    String completeOrder(String orderId);

    Page<OrderDto> getMemberOrders(OrderSearchRequestDto searchRequest, Pageable pageable);

    Page<OrderDto> getAllOrders(OrderSearchRequestDto searchRequest, Pageable pageable);

    OrderDetail getOrderDetail(String orderId);

    String getNonMemberOrderId(NonMemberOrderDetailAccessRequestDto accessRequest);

    List<OrderStatus> getOrderStatuses();

    void cancelOrderProduct(String orderProductId, OrderCancelRequestDto cancelRequest);

    void modifyOrderStatus(String orderId, StatusDto status);

    void completeOrderDelivery(String orderId, Long deliveryId);

//    void requestReturnOrder(String orderId, OrderReturnRequestDto returnRequest);
    void requestReturnOrderProduct(String orderId, Long orderProductId, OrderReturnRequestDto returnRequest);

    void completeReturningOrder(String orderId);

    Page<OrderProductReturnDto> getOrderProductReturns(OrderReturnSearchRequestDto searchRequest, Pageable pageable);

    void completeReturningOrderProduct(String orderId, Long orderProductId);

}
