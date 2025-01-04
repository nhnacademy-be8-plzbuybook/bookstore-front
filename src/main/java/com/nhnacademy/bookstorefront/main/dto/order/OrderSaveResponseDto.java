package com.nhnacademy.bookstorefront.main.dto.order;

import java.math.BigDecimal;

public record OrderSaveResponseDto (String orderId, BigDecimal amount, String orderName) {
}
