package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.dto.OrderSaveResponseDto;
import com.nhnacademy.bookstorefront.main.dto.order.OrderDto;
import com.nhnacademy.bookstorefront.main.dto.order.OrderSearchRequestDto;
import com.nhnacademy.bookstorefront.main.dto.order.orderRequests.MemberOrderRequestDto;
import com.nhnacademy.bookstorefront.main.dto.order.orderRequests.NonMemberOrderRequestDto;
import com.nhnacademy.bookstorefront.main.service.OrderService;
import lombok.RequiredArgsConstructor;
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

    /**
     * 비회원 주문페이지
     *
     * @return
     */
    @GetMapping("/non-member/order/receipt")
    public String OrderReceipt() {
        return "order/non_member_receipt";
    }


    /**
     * 회원 주문페이지
     *
     * @return
     */
    @GetMapping("/order/receipt")
    public String memberOrderReceipt() {
        return "order/member_receipt";
    }


    /**
     * 주문상세내역 조회
     *
     * @param orderId
     * @param model
     * @return
     */
    @GetMapping("/orders/{order-id}")
    public String orderDetail(@PathVariable("order-id") String orderId,
                              Model model) {
        //TODO: 주문상세정보 불러오기

        return "order/detail";
    }


    /**
     * 내 주문목록 조회
     *
     * @param searchRequest
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/my/orders")
    public String getMemberOrders(@ModelAttribute OrderSearchRequestDto searchRequest,
                                  Pageable pageable,
                                  Model model) {
        Page<OrderDto> orderPage = orderService.getMemberOrders(searchRequest, pageable);
        model.addAttribute("orderPage", orderPage);

        return "order/myOrderList";
    }


    /**
     * 전체 주문목록 조회 (관리자용)
     *
     * @param searchRequest
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/admin/orders")
    public String getAllOrders(@ModelAttribute OrderSearchRequestDto searchRequest,
                               Pageable pageable,
                               Model model) {
        Page<OrderDto> orderList = orderService.getAllOrders(searchRequest, pageable);
        model.addAttribute("orderList", orderList);

        return "order/allOrderList";
    }


    /**
     * 비회원 주문요청
     *
     * @param orderRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/api/orders/non-member")
    public OrderSaveResponseDto nonMemberOrder(@RequestBody NonMemberOrderRequestDto orderRequest) {
        OrderSaveResponseDto orderSaveResponse = orderService.requestNonMemberOrder(orderRequest);
        return orderSaveResponse;
    }


    /**
     * 회원 주문요청
     *
     * @param orderRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/api/orders")
    public OrderSaveResponseDto memberOrder(@RequestBody MemberOrderRequestDto orderRequest) {
//        OrderSaveResponseDto orderSaveResponse = new OrderSaveResponseDto("13123123", new BigDecimal("10000"), "수학의 정석 외 1건");
        OrderSaveResponseDto orderSaveResponse = orderService.requestMemberOrder(orderRequest);
        return orderSaveResponse;
//        return null;
    }


    /**
     * 회원 주문완료
     *
     * @param orderId
     * @return
     */
    @PostMapping("/api/orders/{order-id}/complete")
    public ResponseEntity<String> completeOrder(@PathVariable("order-id") String orderId) {
        String completedOrderId = orderService.completeOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(completedOrderId);
    }
}
