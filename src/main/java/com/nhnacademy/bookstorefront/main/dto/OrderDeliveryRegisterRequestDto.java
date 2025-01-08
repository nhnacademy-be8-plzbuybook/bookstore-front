package com.nhnacademy.bookstorefront.main.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderDeliveryRegisterRequestDto {
    private String deliveryCompany;
    private String trackingNumber;
}
