package com.nhnacademy.bookstorefront.main.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class OrderReturnRequestDto {
    private String reason;
    private String trackingNumber;
}
