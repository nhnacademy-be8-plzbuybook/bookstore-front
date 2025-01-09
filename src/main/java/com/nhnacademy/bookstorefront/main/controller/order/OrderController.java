package com.nhnacademy.bookstorefront.main.controller.order;

import com.nhnacademy.bookstorefront.main.client.BookClient;
import com.nhnacademy.bookstorefront.main.client.MemberClient;
import com.nhnacademy.bookstorefront.main.client.OrderClient;
import com.nhnacademy.bookstorefront.main.dto.BookDetailResponseDto;
import com.nhnacademy.bookstorefront.main.dto.Member.MemberAddressResponseDto;
import com.nhnacademy.bookstorefront.main.dto.order.*;
import com.nhnacademy.bookstorefront.main.dto.order.orderRequests.MemberOrderRequestDto;
import com.nhnacademy.bookstorefront.main.dto.order.orderRequests.NonMemberOrderRequestDto;
import com.nhnacademy.bookstorefront.main.service.DeliveryFeePolicyService;
import com.nhnacademy.bookstorefront.main.service.OrderService;
import com.nhnacademy.bookstorefront.main.service.WrappingPaperService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
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
    private final OrderClient orderClient;

    /**
     * 비회원 주문페이지
     *
     * @return
     */
    @GetMapping("/non-member/order/receipt")
    public String nonMemberOrderReceipt(@RequestParam("productId") List<Long> productId,
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
        return "order/user/nonMember/order_receipt";
    }

    /**
     * 비회원주문 상세조회 접근 페이지
     *
     * @return
     */
    @GetMapping("/non-member/order")
    public String nonMemberOrderDetailAccess() {
        return "order/user/nonMember/order_detail_access";
    }


    @PostMapping("/api/non-member/order/access")
    public String accessNonMemberOrderDetail(@ModelAttribute NonMemberOrderDetailAccessRequestDto accessRequest) {
        String orderId = orderService.getNonMemberOrderId(accessRequest);

        return "redirect:/orders/" + orderId;
    }

    /**
     * 회원 주문페이지
     *
     * @return
     */
    @GetMapping("/order/receipt")
    public String memberOrderReceipt(@RequestParam("productId") List<Long> productId,
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
        return "order/user/member/order_receipt";
    }


    /**
     * 사용자 주문 상세조회
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

        return "order/user/order_detail";
    }


    /**
     * 회원 주문목록 조회
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

        return "order/user/member/order_list";
    }


    /**
     * 관리자 주문목록 조회
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

        return "order/admin/order_list";
    }


    /**
     * 관리자 주문 상세 조회
     *
     * @param orderId
     * @param model
     * @return
     */
    @GetMapping("/admin/orders/{order-id}")
    public String getAdminOrderDetail(@PathVariable("order-id") String orderId,
                                      Model model) {
        OrderDetail orderDetail = orderService.getOrderDetail(orderId);
        List<String> orderStatuses = orderService.getOrderStatuses();

        model.addAttribute("orderDetail", orderDetail);
        model.addAttribute("orderStatuses", orderStatuses);

        return "order/admin/order_detail";
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

    @PutMapping("/api/orders/order-products/{order-product-id}/purchase-confirm")
    public ResponseEntity<Void> confirmPurchase(@PathVariable("order-product-id") Long orderProductId) {
        return orderClient.confirmPurchase(orderProductId);
    }

    @PatchMapping("/api/orders/{order-id}/status")
    public ResponseEntity<Void> changeOrderStatus(@PathVariable("order-id") String orderId,
                                                  @RequestBody StatusDto status) {
        orderService.modifyOrderStatus(orderId, status);

        return ResponseEntity.ok().build();
    }

}
