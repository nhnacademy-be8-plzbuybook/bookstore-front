package com.nhnacademy.bookstorefront.main.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
public class DeliveryFeePolicyModifyRequestDto {
    private String name;
    private BigDecimal defaultDeliveryFee;
    private BigDecimal freeDeliveryThreshold;
}
