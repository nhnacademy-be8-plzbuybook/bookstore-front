package com.nhnacademy.bookstorefront.main.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OrderReturnRequestDto {
    private String reason;
    private String tackingNumber;
}
