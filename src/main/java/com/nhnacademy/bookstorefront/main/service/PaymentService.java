package com.nhnacademy.bookstorefront.main.service;

import com.nhnacademy.bookstorefront.main.dto.payment.PaymentConfirmResponseDto;
import com.nhnacademy.bookstorefront.main.dto.payment.PaymentConfirmRequestDto;

public interface PaymentService {
    PaymentConfirmResponseDto confirmPayment(PaymentConfirmRequestDto confirmRequest);
}
