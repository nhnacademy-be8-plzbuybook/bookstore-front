package com.nhnacademy.bookstorefront.main.client;

import com.nhnacademy.bookstorefront.main.dto.order.OrderSaveResponseDto;
import com.nhnacademy.bookstorefront.main.dto.order.OrderDetail;
import com.nhnacademy.bookstorefront.main.dto.order.OrderDto;
import com.nhnacademy.bookstorefront.main.dto.order.OrderSearchRequestDto;
import com.nhnacademy.bookstorefront.main.dto.order.orderRequests.MemberOrderRequestDto;
import com.nhnacademy.bookstorefront.main.dto.order.orderRequests.NonMemberOrderRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "GATEWAY", contextId = "orderClient")
public interface OrderClient {

    @GetMapping("/api/orders/my")
    ResponseEntity<Page<OrderDto>> getMemberOrders(@RequestParam(required = false) OrderSearchRequestDto searchRequest, Pageable pageable);

    @GetMapping("/api/orders")
    ResponseEntity<Page<OrderDto>> getAllOrders(@ModelAttribute OrderSearchRequestDto searchRequest, Pageable pageable);

    @PostMapping("/api/orders/non-member")
    ResponseEntity<OrderSaveResponseDto> requestNonMemberOrder(@RequestBody NonMemberOrderRequestDto orderSaveRequest);

    @PostMapping("/api/orders/member")
    ResponseEntity<OrderSaveResponseDto> requestMemberOrder(@RequestBody MemberOrderRequestDto orderSaveRequest);

    @PostMapping("/api/orders/{order-id}/complete")
    ResponseEntity<String> completeOrder(@PathVariable("order-id") String orderId);

    @GetMapping("/api/orders/{order-id}")
    ResponseEntity<OrderDetail> getOrderDetail(@PathVariable("order-id") String orderId);
}