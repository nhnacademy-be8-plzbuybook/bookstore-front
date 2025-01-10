package com.nhnacademy.bookstorefront.main.dto.cart.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ReadCartBookResponse(
        Long cartId,
        Long cartBookId,
        Long sellingBookId,
        String bookTitle,
        BigDecimal sellingBookPrice,
        String imageUrl,
        int quantity,
        int sellingBookStock,
        boolean sellingBookPackageable,
        boolean used
) {
}
