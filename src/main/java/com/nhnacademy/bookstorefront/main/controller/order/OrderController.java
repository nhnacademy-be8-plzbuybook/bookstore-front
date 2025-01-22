package com.nhnacademy.bookstorefront.main.controller.order;

import com.nhnacademy.bookstorefront.main.client.BookClient;
import com.nhnacademy.bookstorefront.main.client.MemberClient;
import com.nhnacademy.bookstorefront.main.client.OrderClient;
import com.nhnacademy.bookstorefront.main.client.PointClient;
import com.nhnacademy.bookstorefront.main.dto.BookDetailResponseDto;
import com.nhnacademy.bookstorefront.main.dto.Member.MemberAddressResponseDto;
import com.nhnacademy.bookstorefront.main.dto.Member.MemberCouponGetResponseDto;
import com.nhnacademy.bookstorefront.main.dto.coupon.CouponCalculationRequestDto;
import com.nhnacademy.bookstorefront.main.dto.coupon.CouponCalculationResponseDto;
import com.nhnacademy.bookstorefront.main.dto.order.*;
import com.nhnacademy.bookstorefront.main.dto.order.orderRequests.OrderRequestDto;
import com.nhnacademy.bookstorefront.main.enums.OrderStatus;
import com.nhnacademy.bookstorefront.main.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class OrderController {
    private final OrderService orderService;
    private final BookClient bookClient;
    private final WrappingPaperService wrappingPaperService;
    private final DeliveryFeePolicyService deliveryFeePolicyService;
    private final MemberClient memberClient;
    private final OrderClient orderClient;
    private final PointClient pointClient;
    private final CouponService couponService;
    private final AuthenticationService authenticationService;
    private final MemberService memberService;

    @GetMapping("/admin/order-product-returns")
    public String adminOrderReturns(@ModelAttribute OrderReturnSearchRequestDto searchRequest,
                                    Model model, Pageable pageable) {
        Page<OrderProductReturnDto> orderProductReturnPage = orderService.getOrderProductReturns(searchRequest, pageable);

        model.addAttribute("orderProductReturns", orderProductReturnPage.getContent());
        model.addAttribute("currentPage", pageable.getPageNumber());
        model.addAttribute("totalPages", orderProductReturnPage.getTotalPages());
        model.addAttribute("totalItems", orderProductReturnPage.getTotalElements());


        return "order/admin/return/order_return_list";
    }

    /**
     * 주문반품 폼
     *
     * @param orderId
     * @param model
     * @return
     */
    @GetMapping("/orders/{order-id}/order-products/{order-product-id}/return")
    public String orderReturnForm(@PathVariable("order-id") String orderId,
                                  @PathVariable("order-product-id") Long orderProductId,
                                  @RequestParam("quantity") int quantity,
                                  Model model) {
        model.addAttribute("orderId", orderId);
        model.addAttribute("orderProductId", orderProductId);
        model.addAttribute("quantity", quantity);
        return "order/return_form";
    }

    /**
     * 주문상품 반품 요청
     *
     * @param orderId
     * @param orderProductId
     * @param returnRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/api/orders/{order-id}/order-products/{order-product-id}/return")
    public ResponseEntity<Void> requestReturnOrder(@PathVariable("order-id") String orderId,
                                                   @PathVariable("order-product-id") Long orderProductId,
                                                   @RequestBody OrderReturnRequestDto returnRequest) {
        orderService.requestReturnOrderProduct(orderId, orderProductId, returnRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 주문상품 반품요청 완료처리
     *
     * @param orderId
     * @return
     */
    @ResponseBody
    @PostMapping("/api/orders/{order-id}/order-products/{order-product-id}/return/complete")
    public ResponseEntity<Void> completeReturnOrder(@PathVariable("order-id") String orderId,
                                                    @PathVariable("order-product-id") Long orderProductId) {
        orderService.completeReturningOrderProduct(orderId, orderProductId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


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

        return "redirect:/orders/" + orderId;   }

    /**
     * 회원 주문페이지
     *
     * @return
     */
    @GetMapping("/order/receipt")
    public String memberOrderReceipt(@RequestParam("productId") List<Long> productId,
                                     @RequestParam("quantity") List<Integer> quantity,
                                     HttpServletRequest request,
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
        Integer availablePoints = pointClient.getAvailablePoints().getBody();
        String email = authenticationService.getEmailFromToken(request);
        model.addAttribute("email", email);
        model.addAttribute("wrappingPapers", wrappingPaperService.getWrappingPapers());
        model.addAttribute("books", books);
        model.addAttribute("deliveryFeePolicy", deliveryFeePolicyService.getGeneralPolicy());
        model.addAttribute("addressList", addressList);
        model.addAttribute("defaultAddress", defaultAddress);
        model.addAttribute("availablePoints", availablePoints);
        return "order/user/member/order_receipt";
    }

    /**
     * 회원이 사용가능한 쿠폰 목록 조회 할 수 있는 주문상품 쿠폰적용 팝업 페이지
     *
     * @param price
     * @param quantity
     * @param page
     * @param pageSize
     */
    @GetMapping("/order/receipt/coupon-popup")
    public String orderReceiptCouponPopup(@RequestParam("productId") Long productId,
                                          @RequestParam BigDecimal price,
                                          @RequestParam Integer quantity,
                                          @RequestParam int page,
                                          @RequestParam int pageSize,
                                          HttpServletRequest request,
                                          Model model) {

        String email = authenticationService.getEmailFromToken(request);
        Pageable pageable = PageRequest.of(page, pageSize);

        // 이메일로 회원 식별키 조회
        Long memberId = memberService.getMemberIdByEmail(email);

        // 회원이 보유한 쿠폰 조회
        Page<MemberCouponGetResponseDto> coupons = couponService.getUnusedMemberCouponsByMemberId(memberId, pageable);

        model.addAttribute("memberId", memberId);
        model.addAttribute("productId", productId);
        model.addAttribute("email", email);
        model.addAttribute("price", price);
        model.addAttribute("quantity", quantity);
        model.addAttribute("coupons", coupons);
        model.addAttribute("currentPage", page);
        model.addAttribute("PageSize", pageSize);
        model.addAttribute("totalPages", coupons != null ? coupons.getTotalPages() : 0);

        return "order/user/member/order_receipt_coupon_popup";
    }

    /**
     * 주문상품에 할인쿠폰 계산적용
     */
    @PostMapping("/order/receipt/coupon-popup/apply")
    public ResponseEntity<CouponCalculationResponseDto> orderReceiptCouponPopupApply(
            @RequestParam Long couponId,
            @RequestBody CouponCalculationRequestDto requestDto,
            HttpServletRequest request,
            Model model
    ) {
        String email = authenticationService.getEmailFromToken(request);
        Long memberId = memberService.getMemberIdByEmail(email);

        CouponCalculationResponseDto responseDto = couponService.applyOrderProductCoupon(couponId, requestDto, request);

        model.addAttribute("email", email);
        model.addAttribute("memberId", memberId);
        model.addAttribute("data", responseDto);
        return ResponseEntity.ok(responseDto);
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
    public String getMemberOrders(OrderSearchRequestDto searchRequest,
                                  Pageable pageable,
                                  Model model) {
        Page<OrderDto> orderPage = orderService.getMemberOrders(searchRequest, pageable);
        List<OrderStatus> orderStatusList = orderService.getOrderStatuses();
        model.addAttribute("orderPage", orderPage);
        model.addAttribute("orderStatusList", orderStatusList);
        model.addAttribute("currentPage", pageable.getPageNumber());
        model.addAttribute("totalPages", orderPage.getTotalPages());
        model.addAttribute("totalItems", orderPage.getTotalElements());



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
        Page<OrderDto> orderPage = orderService.getAllOrders(searchRequest, pageable);
        List<OrderStatus> orderStatuses = orderService.getOrderStatuses();

        model.addAttribute("orderPage", orderPage);
        model.addAttribute("orderStatuses", orderStatuses);
        model.addAttribute("currentPage", pageable.getPageNumber());
        model.addAttribute("totalPages", orderPage.getTotalPages());
        model.addAttribute("totalItems", orderPage.getTotalElements());

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
        List<OrderStatus> orderStatuses = orderService.getOrderStatuses();

        model.addAttribute("orderDetail", orderDetail);
        model.addAttribute("orderStatuses", orderStatuses);

        return "order/admin/order_detail";
    }


    @ResponseBody
    @PostMapping("/api/orders")
    public OrderSaveResponseDto requestOrder(@RequestBody OrderRequestDto orderRequest) {
        OrderSaveResponseDto orderSaveResponse = orderService.requestOrder(orderRequest);
        return orderSaveResponse;
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

        if (status.getStatus() == OrderStatus.RETURN_COMPLETED) {
            orderService.completeReturningOrder(orderId);
        } else if (status.getStatus() == OrderStatus.DELIVERED) {
            completeOrderDelivery(orderId, 1L); // deliveryId 아직 필요하지 모르겠어서 하드코딩
        } else {
            orderService.modifyOrderStatus(orderId, status);
        }

        return ResponseEntity.ok().build();
    }


    @ResponseBody
    @PostMapping("/api/orders/{order-id}/deliveries/{delivery-id}/complete")
    public ResponseEntity<Void> completeOrderDelivery(@PathVariable("order-id") String orderId,
                                                      @PathVariable("delivery-id") Long deliveryId) {
        orderService.completeOrderDelivery(orderId, deliveryId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

//    @ResponseBody
//    @PostMapping("/api/orders/{order-id}/return")
//    public ResponseEntity<Void> requestReturnOrder(@PathVariable("order-id") String orderId,
//                                                   @RequestBody OrderReturnRequestDto returnRequest) {
//        orderService.requestReturnOrder(orderId, returnRequest);
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }


    @GetMapping("/order/{order-id}/cancel")
    public String orderCancellationPage(@PathVariable("order-id") String orderId,
                                        Model model) {
        OrderDetail orderDetail = orderService.getOrderDetail(orderId);
        List<OrderProductDto> orderProducts = orderDetail.getOrderProducts();
        model.addAttribute("orderProducts", orderProducts);
        model.addAttribute("orderId", orderId);

        return "order/cancel_form";
    }


    @ResponseBody
    @PostMapping("/api/orders/{order-id}/cancel")
    public ResponseEntity<?> cancelOrderProducts(@PathVariable("order-id") String orderId,
                                                 @RequestBody OrderCancelRequestDto cancelRequest) {
        orderService.cancelOrderProduct(orderId, cancelRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
