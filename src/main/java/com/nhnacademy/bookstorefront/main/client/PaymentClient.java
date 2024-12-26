package com.nhnacademy.bookstorefront.main.client;

import com.nhnacademy.bookstorefront.main.dto.payment.SaveAmountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(name = "BOOKSTORE-DEV1")
//public interface PaymentClient {
//
//    @PostMapping("/api/payments/save-payment")
//    ResponseEntity<?> savePaymentTemporary(@RequestBody SaveAmountDto saveAmountDto);
//}