package com.nhnacademy.bookstorefront.main.service.impl;

import com.nhnacademy.bookstorefront.main.client.PaymentClient;
import com.nhnacademy.bookstorefront.main.dto.PaymentConfirmResponseDto;
import com.nhnacademy.bookstorefront.main.dto.payment.PaymentConfirmRequestDto;
import com.nhnacademy.bookstorefront.main.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentClient paymentClient;

    @Override
    public PaymentConfirmResponseDto confirmPayment(PaymentConfirmRequestDto confirmRequest) {

        ResponseEntity<PaymentConfirmResponseDto> responseEntity = paymentClient.confirmPayment(confirmRequest);
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("결제승인이 실패했습니다.");
        }
        return responseEntity.getBody();
    }
}
