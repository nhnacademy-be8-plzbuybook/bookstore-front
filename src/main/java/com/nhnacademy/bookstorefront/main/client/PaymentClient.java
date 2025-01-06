package com.nhnacademy.bookstorefront.main.client;

import com.nhnacademy.bookstorefront.main.dto.payment.PaymentConfirmResponseDto;
import com.nhnacademy.bookstorefront.main.dto.payment.PaymentConfirmRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "GATEWAY", contextId = "paymentClient")
public interface PaymentClient {

    /** 결제승인 요청 */
    @PostMapping("/api/payments/confirm/widget")
    ResponseEntity<PaymentConfirmResponseDto> confirmPayment(@RequestBody PaymentConfirmRequestDto confirmRequest);
}