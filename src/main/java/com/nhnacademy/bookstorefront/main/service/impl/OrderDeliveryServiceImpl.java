package com.nhnacademy.bookstorefront.main.service.impl;

import com.nhnacademy.bookstorefront.main.client.OrderClient;
import com.nhnacademy.bookstorefront.main.dto.OrderDeliveryRegisterRequestDto;
import com.nhnacademy.bookstorefront.main.service.OrderDeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderDeliveryServiceImpl implements OrderDeliveryService {
    private final OrderClient orderClient;

    @Override
    public void registerOrderDelivery(String orderId, OrderDeliveryRegisterRequestDto registerRequest) {
        ResponseEntity<Void> response = orderClient.registerOrderDelivery(orderId, registerRequest);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("주문배송 등록 중 오류가 발생했습니다.");
        }
    }
}
