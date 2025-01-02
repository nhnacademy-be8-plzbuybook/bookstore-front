package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.dto.OrderSaveResponseDto;
import com.nhnacademy.bookstorefront.main.dto.order.OrderDto;
import com.nhnacademy.bookstorefront.main.dto.order.OrderSearchRequestDto;
import com.nhnacademy.bookstorefront.main.dto.order.orderRequests.NonMemberOrderRequestDto;
import com.nhnacademy.bookstorefront.main.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/non-member/order/receipt")
    public String OrderReceipt() {
        return "order/non_member_receipt";
    }

    @GetMapping("/orders/{order-id}")
    public String orderDetail(@PathVariable("order-id") String orderId,
                              Model model) {
        //TODO: 주문상세정보 불러오기

        return "order/detail";
    }

    @GetMapping("/my/orders")
    public String getMemberOrders(@ModelAttribute OrderSearchRequestDto searchRequest,
                                  Pageable pageable,
                                  Model model) {
        Page<OrderDto> orderPage = orderService.getOrders(searchRequest, pageable);
        model.addAttribute("orderPage", orderPage);

        return "order/myOrderList";
    }


    @ResponseBody
    @PostMapping("/api/orders/non-member")
    public OrderSaveResponseDto nonMemberOrder(@RequestBody NonMemberOrderRequestDto orderRequest) {
//        OrderSaveResponseDto orderSaveResponse = new OrderSaveResponseDto("13123123", new BigDecimal("10000"), "수학의 정석 외 1건");
        OrderSaveResponseDto orderSaveResponse = orderService.requestNonMemberOrder(orderRequest);
        return orderSaveResponse;
//        return null;
    }


    @PostMapping("/api/orders/{order-id}/complete")
    public ResponseEntity<String> completeOrder(@PathVariable("order-id") String orderId) {
        String completedOrderId = orderService.completeOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(completedOrderId);
    }


}
