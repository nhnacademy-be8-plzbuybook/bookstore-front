package com.nhnacademy.bookstorefront.main.dto.order;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderReturnDto {
    private Long id;
    private String reason;
    private String trackingNumber;
    private LocalDateTime requestedAt;
    private LocalDateTime completedAt;
    private String orderId;
}
