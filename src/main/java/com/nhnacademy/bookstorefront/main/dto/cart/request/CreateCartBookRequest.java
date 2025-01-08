package com.nhnacademy.bookstorefront.main.dto.cart.request;

import lombok.Builder;

@Builder
public record CreateCartBookRequest(
        Long sellingBookId,
        int quantity
) {
}
