package com.nhnacademy.bookstorefront.main.service.impl;

import com.nhnacademy.bookstorefront.main.client.OrderClient;
import com.nhnacademy.bookstorefront.main.dto.OrderSaveResponseDto;
import com.nhnacademy.bookstorefront.main.dto.order.OrderDto;
import com.nhnacademy.bookstorefront.main.dto.order.OrderSaveRequestDto;
import com.nhnacademy.bookstorefront.main.dto.order.OrderSearchRequestDto;
import com.nhnacademy.bookstorefront.main.service.OrderService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    private final OrderClient orderClient;


    @Override
    public OrderSaveResponseDto requestOrder(OrderSaveRequestDto orderSaveRequestDto) {
        try {
//            ResponseEntity<OrderSaveResponseDto> response = authenticationClient.order(orderSaveRequestDto);
            ResponseEntity<OrderSaveResponseDto> response = orderClient.getAllOrders(orderSaveRequestDto);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("주문이 실패했습니다.");
            }
            return response.getBody();

        } catch (FeignException e) {
            log.error("feignClient error: {}", e.getMessage());
            throw new RuntimeException("주문서버에 네트워크 오류가 발생했습니다.");
        }
    }

    @ResponseBody
    @Override
    public String completeOrder(String orderId) {
        try {
            ResponseEntity<?> response = orderClient.completeOrder(orderId);

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("주문이 실패했습니다.");
            }
            return orderId;

        } catch (FeignException e) {
            throw new RuntimeException("주문서버에 네트워크 오류가 발생했습니다.");
        }
    }

    @Override
    public Page<OrderDto> getOrders(OrderSearchRequestDto searchRequest, Pageable pageable) {
        ResponseEntity<Page<OrderDto>> response = orderClient.getMemberOrders(searchRequest, pageable);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("페이지를 가져오는 중 오류가 발생했습니다.");
        }

        return response.getBody();
    }
}
