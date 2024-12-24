package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.dto.OrderSaveResponseDto;
import com.nhnacademy.bookstorefront.main.dto.order.OrderSaveRequestDto;
import com.nhnacademy.bookstorefront.main.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Controller
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/order/receipt")
    public String OrderReceipt() {
        return "order/receipt";
    }

    @ResponseBody
    @PostMapping("/api/orders")
    public String Order(@RequestBody OrderSaveRequestDto orderSaveRequestDto) {
        OrderSaveResponseDto orderSaveResponse = new OrderSaveResponseDto("13123123", new BigDecimal("10000"), "수학의 정석 외 1건");

//        OrderSaveResponseDto orderSaveResponse = orderService.requestOrder(orderSaveRequestDto);

        // 결제 위젯으로 리다이렉트할 URI 생성
        URI paymentUri = UriComponentsBuilder.fromUriString("/payments/toss")
                .queryParam("orderId", orderSaveResponse.orderId())
                .queryParam("amount", orderSaveResponse.amount())
                .queryParam("orderName", URLEncoder.encode(orderSaveResponse.orderName(), StandardCharsets.UTF_8))
                .build()
                .toUri();

        // 리다이렉트할 전체 URL을 반환
//        return orderSaveResponse;
        return URLEncoder.encode(String.format("orderId=%s&amount=%s&orderName=%s", orderSaveResponse.orderId(), orderSaveResponse.amount(), orderSaveResponse.orderName()), StandardCharsets.UTF_8).toString();
    }
}
