package com.nhnacademy.bookstorefront.main.client;

import com.nhnacademy.bookstorefront.main.dto.OrderSaveResponseDto;
import com.nhnacademy.bookstorefront.main.dto.order.OrderDto;
import com.nhnacademy.bookstorefront.main.dto.order.OrderSaveRequestDto;
import com.nhnacademy.bookstorefront.main.dto.order.OrderSearchRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "GATEWAY", contextId = "orderClient")
public interface OrderClient {

    @GetMapping("/api/my/orders")
    ResponseEntity<Page<OrderDto>> getMemberOrders(@ModelAttribute OrderSearchRequestDto searchRequest, Pageable pageable);

    @PostMapping("/api/orders")
    ResponseEntity<OrderSaveResponseDto> getAllOrders(@RequestBody OrderSaveRequestDto orderSaveRequest);

    @PostMapping("/api/orders/{order-id}/complete")
    ResponseEntity<?> completeOrder(@RequestParam("order-id") String orderId);
}