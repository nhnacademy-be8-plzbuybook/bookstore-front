package com.nhnacademy.bookstorefront.main.service.impl;

import com.nhnacademy.bookstorefront.main.client.OrderClient;
import com.nhnacademy.bookstorefront.main.dto.PaymentConfirmResponseDto;
import com.nhnacademy.bookstorefront.main.dto.payment.PaymentConfirmRequestDto;
import com.nhnacademy.bookstorefront.main.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {
    private final OrderClient orderClient;

    @Override
    public PaymentConfirmResponseDto confirmPayment(PaymentConfirmRequestDto confirmRequest) {
        ResponseEntity<?> responseEntity = orderClient.confirmPayment(confirmRequest);

        return null;
    }
}
