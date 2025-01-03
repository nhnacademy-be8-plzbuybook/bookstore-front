package com.nhnacademy.bookstorefront.main.dto.order;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class PaymentDto {
    private BigDecimal amount;
    private String method;
    private String easyPayProvider;
    private LocalDateTime paidAt;

    public PaymentDto(BigDecimal amount, String method, String easyPayProvider, LocalDateTime paidAt) {
        this.amount = amount;
        this.method = method;
        this.easyPayProvider = easyPayProvider;
        this.paidAt = paidAt;
    }
}
