package com.nhnacademy.bookstorefront.main.client;

import com.nhnacademy.bookstorefront.main.dto.OrderSaveResponseDto;
import com.nhnacademy.bookstorefront.main.dto.order.OrderSaveRequestDto;
import com.nhnacademy.bookstorefront.main.dto.payment.PaymentConfirmRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "BOOKSTORE-BACK2")
public interface OrderClient {

    @PostMapping("/api/orders")
    ResponseEntity<OrderSaveResponseDto> order(@RequestBody OrderSaveRequestDto orderSaveRequest);

    /** 결제승인 요청 */
    @PostMapping("/api/payments/confirm/widget")
    ResponseEntity<?> confirmPayment(@RequestBody PaymentConfirmRequestDto confirmRequest);
}
