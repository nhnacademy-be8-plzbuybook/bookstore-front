package com.nhnacademy.bookstorefront.main.dto.order;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderDeliveryDto {
    private String deliveryCompany;
    private String trackingNumber;
    private LocalDateTime registeredAt;

    public OrderDeliveryDto(String deliveryCompany, String trackingNumber, LocalDateTime registeredAt) {
        this.deliveryCompany = deliveryCompany;
        this.trackingNumber = trackingNumber;
        this.registeredAt = registeredAt;
    }
}
