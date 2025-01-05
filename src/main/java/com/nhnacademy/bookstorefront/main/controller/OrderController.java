package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.BookClient;
import com.nhnacademy.bookstorefront.main.client.MemberClient;
import com.nhnacademy.bookstorefront.main.dto.BookDetailResponseDto;
import com.nhnacademy.bookstorefront.main.dto.Member.MemberAddressResponseDto;
import com.nhnacademy.bookstorefront.main.dto.order.*;
import com.nhnacademy.bookstorefront.main.dto.order.orderRequests.MemberOrderRequestDto;
import com.nhnacademy.bookstorefront.main.dto.order.orderRequests.NonMemberOrderRequestDto;
import com.nhnacademy.bookstorefront.main.service.DeliveryFeePolicyService;
import com.nhnacademy.bookstorefront.main.service.OrderService;
import com.nhnacademy.bookstorefront.main.service.WrappingPaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class OrderController {
    private final OrderService orderService;
    private final BookClient bookClient;
    private final WrappingPaperService wrappingPaperService;
    private final DeliveryFeePolicyService deliveryFeePolicyService;
    private final MemberClient memberClient;

    /**
     * 비회원 주문페이지
     *
     * @return
     */
    @GetMapping("/non-member/order/receipt")
    public String nonMemberOrderReceipt(@RequestParam("productId")List<Long> productId,
                                        @RequestParam("quantity") List<Integer> quantity,
                                        Model model) {
        List<OrderReceiptProduct> books = new ArrayList<>();
        for (int i = 0; i < productId.size(); i++) {
            BookDetailResponseDto bookDetail = bookClient.getSellingBook(productId.get(i));
            books.add(new OrderReceiptProduct(bookDetail, quantity.get(i)));
        }

        model.addAttribute("wrappingPapers", wrappingPaperService.getWrappingPapers());
        model.addAttribute("books", books);
        model.addAttribute("deliveryFeePolicy", deliveryFeePolicyService.getGeneralPolicy());
        return "order/nonMember/receipt";
    }


    /**
     * 회원 주문페이지
     *
     * @return
     */
    @GetMapping("/order/receipt")
    public String memberOrderReceipt(@RequestParam("productId")List<Long> productId,
                                     @RequestParam("quantity") List<Integer> quantity,
                                     Model model) {

        // 책 정보
        List<OrderReceiptProduct> books = new ArrayList<>();
        for (int i = 0; i < productId.size(); i++) {
            BookDetailResponseDto bookDetail = bookClient.getSellingBook(productId.get(i));
            books.add(new OrderReceiptProduct(bookDetail, quantity.get(i)));
        }

        // 회원주소
        List<MemberAddressResponseDto> addressList = memberClient.getAddressListByMemberEmail();
        // 회원기본주소
        MemberAddressResponseDto defaultAddress = addressList.stream()
                .filter(MemberAddressResponseDto::getDefaultAddress)
                .findFirst()
                .orElse(null);

        //회원 포인트
        model.addAttribute("wrappingPapers", wrappingPaperService.getWrappingPapers());
        model.addAttribute("books", books);
        model.addAttribute("deliveryFeePolicy", deliveryFeePolicyService.getGeneralPolicy());
        model.addAttribute("addressList", addressList);
        model.addAttribute("defaultAddress", defaultAddress);
        return "order/member/receipt";
    }


    /**
     * 주문 상세조회
     *
     * @param orderId
     * @param model
     * @return
     */
    @GetMapping("/orders/{order-id}")
    public String orderDetail(@PathVariable("order-id") String orderId,
                              Model model) {
        // 주문상세 DTO

        OrderDetail orderDetail = orderService.getOrderDetail(orderId);
        model.addAttribute("orderDetail", orderDetail);

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
    public String getMemberOrders(@RequestParam(required = false) OrderSearchRequestDto searchRequest,
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
