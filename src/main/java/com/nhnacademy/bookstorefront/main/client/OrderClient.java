package com.nhnacademy.bookstorefront.main.client;

import com.nhnacademy.bookstorefront.main.controller.order.OrderProductReturnDto;
import com.nhnacademy.bookstorefront.main.dto.OrderDeliveryRegisterRequestDto;
import com.nhnacademy.bookstorefront.main.dto.order.*;
import com.nhnacademy.bookstorefront.main.dto.order.orderRequests.MemberOrderRequestDto;
import com.nhnacademy.bookstorefront.main.dto.order.orderRequests.NonMemberOrderRequestDto;
import com.nhnacademy.bookstorefront.main.dto.order.orderRequests.OrderRequestDto;
import com.nhnacademy.bookstorefront.main.enums.OrderStatus;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "GATEWAY", contextId = "orderClient")
public interface OrderClient {

    @GetMapping("/api/orders/my")
    ResponseEntity<Page<OrderDto>> getMemberOrders(@SpringQueryMap OrderSearchRequestDto searchRequest,
                                                   Pageable pageable);

    @GetMapping("/api/orders")
    ResponseEntity<Page<OrderDto>> getAllOrders(@SpringQueryMap OrderSearchRequestDto searchRequest,
                                                Pageable pageable);

//    @PostMapping("/api/orders/non-member")
//    ResponseEntity<OrderSaveResponseDto> requestNonMemberOrder(@RequestBody NonMemberOrderRequestDto orderSaveRequest);
//
//    @PostMapping("/api/orders/member")
//    ResponseEntity<OrderSaveResponseDto> requestMemberOrder(@RequestBody MemberOrderRequestDto orderSaveRequest);
@PostMapping("/api/orders/non-member")
ResponseEntity<OrderSaveResponseDto> requestNonMemberOrder(@RequestBody OrderRequestDto orderSaveRequest);

    @PostMapping("/api/orders/member")
    ResponseEntity<OrderSaveResponseDto> requestMemberOrder(@RequestBody OrderRequestDto orderSaveRequest);

    @PostMapping("/api/orders")
    ResponseEntity<OrderSaveResponseDto> requestOrder(@RequestBody OrderRequestDto orderSaveRequest);

    @PostMapping("/api/orders/{order-id}/complete")
    ResponseEntity<String> completeOrder(@PathVariable("order-id") String orderId);

    @GetMapping("/api/orders/{order-id}")
    ResponseEntity<OrderDetail> getOrderDetail(@PathVariable("order-id") String orderId);

    //orderDelivery
    @PostMapping("/api/orders/{order-id}/deliveries")
    ResponseEntity<Void> registerOrderDelivery(@PathVariable("order-id") String orderId,
                                               @Valid @RequestBody OrderDeliveryRegisterRequestDto registerRequest);

    @PostMapping("/api/orders/non-member/access")
    ResponseEntity<String> nonMemberOrderAccess(@RequestBody NonMemberOrderDetailAccessRequestDto accessRequest);

    @PutMapping("/api/orders/order-products/{order-product-id}/status")
    ResponseEntity<Void> patchOrderProductStatus(@RequestBody OrderProductStatusPatchRequestDto statusPatchRequest);

    @PutMapping("/api/orders/order-products/{order-product-id}/purchase-confirm")
    ResponseEntity<Void> confirmPurchase(@PathVariable("order-product-id") Long orderProductId);

    @GetMapping("/api/orders/order-status")
    ResponseEntity<List<OrderStatus>> getOrderStatuses();

    @PutMapping("/api/orders/{order-id}/status")
    ResponseEntity<Void> modifyOrderStatus(@PathVariable("order-id") String orderId,
                                           @RequestBody StatusDto status);

//    @PostMapping("/api/orders/{order-id}/cancel")
//    ResponseEntity<Void> cancelOrder(@PathVariable("order-id") String orderId,
//                                     @RequestBody OrderCancelRequestDto cancelRequest);

    @PostMapping("/api/orders/{order-id}/cancel")
    ResponseEntity<Void> cancelOrderProduct(@PathVariable("order-id") String orderId,
                                            @RequestBody OrderCancelRequestDto cancelRequests);

    @PostMapping("/api/orders/{order-id}/deliveries/{delivery-id}/complete")
    ResponseEntity<Void> completeOrderDelivery(@PathVariable("order-id") String orderId,
                                               @PathVariable("delivery-id") Long deliveryId);

    @PostMapping("/api/orders/{order-id}/return")
    ResponseEntity<Void> requestReturnOrder(@PathVariable("order-id") String orderId,
                                            @RequestBody OrderReturnRequestDto returnRequest);

    @PostMapping("/api/orders/{order-id}/order-products/{order-product-id}/return")
    ResponseEntity<Void> requestReturnOrderProduct(@PathVariable("order-id") String orderId,
                                                   @PathVariable("order-product-id") Long orderProductId,
                                                   @RequestBody OrderReturnRequestDto returnRequest);

    @PostMapping("/api/orders/{order-id}/return/complete")
    ResponseEntity<Void> completeReturningOrder(@PathVariable("order-id") String orderId);

    @GetMapping("/api/orders/order-returns")
    ResponseEntity<Page<OrderReturnDto>> getOrderReturns(@SpringQueryMap OrderReturnSearchRequestDto searchRequest,
                                                         Pageable pageable);

    @GetMapping("/api/orders/order-product-returns")
    ResponseEntity<Page<OrderProductReturnDto>> getOrderProductReturns(@SpringQueryMap OrderReturnSearchRequestDto searchRequest, Pageable pageable);

    @PostMapping("/api/orders/{order-id}/order-products/{order-product-id}/return/complete")
    void completeReturningOrderProduct(@PathVariable("order-id") String orderId,
                                       @PathVariable("order-product-id") Long orderProductId);

}