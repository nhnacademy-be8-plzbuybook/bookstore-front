package com.nhnacademy.bookstorefront.main.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.bookstorefront.common.exception.NonMemberAccessFailException;
import com.nhnacademy.bookstorefront.main.client.OrderClient;
import com.nhnacademy.bookstorefront.main.controller.order.OrderProductReturnDto;

import com.nhnacademy.bookstorefront.main.dto.order.*;
import com.nhnacademy.bookstorefront.main.dto.order.orderRequests.OrderRequestDto;
import com.nhnacademy.bookstorefront.main.enums.OrderStatus;
import com.nhnacademy.bookstorefront.main.service.OrderService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    private final OrderClient orderClient;


    @Override
    public OrderSaveResponseDto requestOrder(OrderRequestDto orderRequest) {
        try {
            ResponseEntity<OrderSaveResponseDto> response = orderClient.requestOrder(orderRequest);
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
            return response.getBody().toString();

        } catch (FeignException e) {
            throw new RuntimeException("주문 중 오류가 발생했습니다.");
        }
    }

    @Override
    public Page<OrderDto> getMemberOrders(OrderSearchRequestDto searchRequest, Pageable pageable) {
        try {
            ResponseEntity<Page<OrderDto>> response = orderClient.getMemberOrders(searchRequest, pageable);
            return response.getBody();
        } catch (FeignException e) {
            throw new RuntimeException("페이지를 가져오는 중 오류가 발생했습니다.");
        }
    }

    @Override
    public Page<OrderDto> getAllOrders(OrderSearchRequestDto searchRequest, Pageable pageable) {
        try {
            ResponseEntity<Page<OrderDto>> response = orderClient.getAllOrders(searchRequest, pageable);
            return response.getBody();
        } catch (FeignException e) {
            throw new RuntimeException("페이지를 가져오는 중 오류가 발생했습니다.");
        }
    }

    @Override
    public OrderDetail getOrderDetail(String orderId) {
        try {
            ResponseEntity<OrderDetail> response = orderClient.getOrderDetail(orderId);
            return response.getBody();
        } catch (FeignException e) {
            throw new RuntimeException("페이지를 가져오는 중 오류가 발생했습니다.");
        }
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

    @Override
    public List<OrderStatus> getOrderStatuses() {
        try {
            ResponseEntity<List<OrderStatus>> response = orderClient.getOrderStatuses();
            return response.getBody();
        } catch (FeignException e) {
            throw new RuntimeException("주문상태를 가져오는 중 오류가 발생했습니다..");
        }
    }

    @Override
    public void cancelOrderProduct(String orderId, OrderCancelRequestDto cancelRequest) {
        try {
            orderClient.cancelOrderProduct(orderId, cancelRequest);
        } catch (FeignException e) {
            throw new RuntimeException("주문상품 취소 중 오류가 발생했습니다.");
        }
    }

    @Override
    public void modifyOrderStatus(String orderId, StatusDto status) {
        try {
            orderClient.modifyOrderStatus(orderId, status);
        } catch (FeignException e) {
            throw new RuntimeException("주문상태 변경 중 오류가 발생했습니다.");
        }
    }

    @Override
    public void completeOrderDelivery(String orderId, Long deliveryId) {
        try {
            orderClient.completeOrderDelivery(orderId, deliveryId);
        } catch (FeignException e) {
            throw new RuntimeException("배송완료처리 중 오류가 발생했습니다.");
        }
    }

    @Override
    public void requestReturnOrderProduct(String orderId, Long orderProductId, OrderReturnRequestDto returnRequest) {
        try {
            orderClient.requestReturnOrderProduct(orderId, orderProductId, returnRequest);
        } catch (FeignException e) {
            String errorMessage = parseFeignException(e);
            throw new RuntimeException(errorMessage, e);
        }
    }

    private String parseFeignException(FeignException e) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> errorResponse = objectMapper.readValue(e.contentUTF8(), new TypeReference<>() {});
            return errorResponse.getOrDefault("message", "알 수 없는 오류가 발생했습니다.");
        } catch (Exception parseException) {
            return "주문 반품 요청 중 오류가 발생했습니다.";
        }
    }

    @Override
    public void completeReturningOrder(String orderId) {
        try {
            orderClient.completeReturningOrder(orderId);
        } catch (FeignException e) {
            throw new RuntimeException("주문 반품요청 중 오류가 발생했습니다.");
        }
    }

    @Override
    public void completeReturningOrderProduct(String orderId, Long orderProductId) {
        try {
            orderClient.completeReturningOrderProduct(orderId, orderProductId);
        } catch (FeignException e) {
            throw new RuntimeException("주문 반품요청 중 오류가 발생했습니다.");
        }
    }

    @Override
    public Page<OrderProductReturnDto> getOrderProductReturns(OrderReturnSearchRequestDto searchRequest, Pageable pageable) {
        try {
            ResponseEntity<Page<OrderProductReturnDto>> response = orderClient.getOrderProductReturns(searchRequest, pageable);
            return response.getBody();
        } catch (FeignException e) {
            throw new RuntimeException("주문반품 목록을 가져오는 중 오류가 발생했습니다.");
        }
    }
}
