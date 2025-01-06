package com.nhnacademy.bookstorefront.main.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class DeliveryFeePolicyDto {
    private BigDecimal defaultDeliveryFee;
    private BigDecimal freeDeliveryThreshold;
}
