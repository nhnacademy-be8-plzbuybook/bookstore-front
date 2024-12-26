package com.nhnacademy.bookstorefront.main.service.impl;

import com.nhnacademy.bookstorefront.main.client.OrderClient;
import com.nhnacademy.bookstorefront.main.dto.OrderSaveResponseDto;
import com.nhnacademy.bookstorefront.main.dto.order.OrderSaveRequestDto;
import com.nhnacademy.bookstorefront.main.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderClient orderClient;

    @Override
    public OrderSaveResponseDto requestOrder(OrderSaveRequestDto orderSaveRequestDto) {
        ResponseEntity<OrderSaveResponseDto> response = orderClient.order(orderSaveRequestDto);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }

        throw new RuntimeException("주문이 실패했습니다.");
    }
}
