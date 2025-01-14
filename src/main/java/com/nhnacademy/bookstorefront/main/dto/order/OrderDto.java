package com.nhnacademy.bookstorefront.main.dto.order;

import com.nhnacademy.bookstorefront.main.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderDto {
    // 주문 id
    private String id;
    // 주문일
    private LocalDateTime orderedAt;
    // 주문상태
    private OrderStatus orderStatus;
    // 주문 상품 썸네일
//    private String thumbNail;
    // 주문명
    private String orderName;
    // 결제금액
    private BigDecimal paymentAmount;
    private String orderNumber;
    // 주문자
    private String orderer;
}