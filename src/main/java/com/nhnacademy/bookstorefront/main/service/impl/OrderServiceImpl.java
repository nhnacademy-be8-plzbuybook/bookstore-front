package com.nhnacademy.bookstorefront.main.service.impl;

import com.nhnacademy.bookstorefront.common.exception.ConflictException;
import com.nhnacademy.bookstorefront.common.exception.NonMemberAccessFailException;
import com.nhnacademy.bookstorefront.main.client.OrderClient;
import com.nhnacademy.bookstorefront.main.dto.order.*;
import com.nhnacademy.bookstorefront.main.dto.order.orderRequests.MemberOrderRequestDto;
import com.nhnacademy.bookstorefront.main.dto.order.orderRequests.NonMemberOrderRequestDto;
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
    public OrderSaveResponseDto requestNonMemberOrder(NonMemberOrderRequestDto orderSaveRequestDto) {
        try {
            ResponseEntity<OrderSaveResponseDto> response = orderClient.requestNonMemberOrder(orderSaveRequestDto);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("주문이 실패했습니다.");
            }
            return response.getBody();

        } catch (FeignException.BadRequest e) {
            log.error("feignClient error: {}", e.getMessage());
            throw new RuntimeException("잘못된 주문형식입니다.");
        } catch (FeignException.Conflict e) {
            throw new ConflictException(e.getMessage());
        }
    }

    @Override
    public OrderSaveResponseDto requestMemberOrder(MemberOrderRequestDto orderSaveRequestDto) {
        try {
            ResponseEntity<OrderSaveResponseDto> response = orderClient.requestMemberOrder(orderSaveRequestDto);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("주문이 실패했습니다.");
            }
            return response.getBody();

        } catch (FeignException.BadRequest e) {
            log.error("feignClient error: {}", e.getMessage());
            throw new RuntimeException("잘못된 주문형식입니다.");
        } catch (FeignException e) {
            log.error("feignClient error: {}", e.getMessage());
            throw new RuntimeException("주문 중 오류가 발생했습니다.");
        }
    }

    @ResponseBody
    @Override
    public String completeOrder(String orderId) {
        try {
            ResponseEntity<String> response = orderClient.completeOrder(orderId);

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("주문이 실패했습니다.");
            }
            return response.getBody().toString();

        } catch (FeignException e) {
            throw new RuntimeException("주문서버에 네트워크 오류가 발생했습니다.");
        }
    }

    @Override
    public Page<OrderDto> getMemberOrders(OrderSearchRequestDto searchRequest, Pageable pageable) {
        ResponseEntity<Page<OrderDto>> response = orderClient.getMemberOrders(searchRequest, pageable);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("페이지를 가져오는 중 오류가 발생했습니다.");
        }

        return response.getBody();
    }

    @Override
    public Page<OrderDto> getAllOrders(OrderSearchRequestDto searchRequest, Pageable pageable) {
        ResponseEntity<Page<OrderDto>> response = orderClient.getAllOrders(searchRequest, pageable);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("페이지를 가져오는 중 오류가 발생했습니다.");
        }
        return response.getBody();
    }

    @Override
    public OrderDetail getOrderDetail(String orderId) {
        ResponseEntity<OrderDetail> response = orderClient.getOrderDetail(orderId);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("페이지를 가져오는 중 오류가 발생했습니다.");
        }
        return response.getBody();
    }

    @Override
    public String getNonMemberOrderId(NonMemberOrderDetailAccessRequestDto accessRequest) {
        try {
            ResponseEntity<String> response = orderClient.nonMemberOrderAccess(accessRequest);
            return response.getBody();
        } catch (FeignException.NotFound | FeignException.Forbidden e) {
            throw new NonMemberAccessFailException("잘못된 주문번호 또는 비밀번호입니다.");
        } catch (FeignException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
