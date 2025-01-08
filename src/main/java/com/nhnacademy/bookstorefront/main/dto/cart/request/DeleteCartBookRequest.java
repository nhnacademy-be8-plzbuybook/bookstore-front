package com.nhnacademy.bookstorefront.main.dto.cart.request;

import lombok.Builder;

@Builder
public record DeleteCartBookRequest(
        Long cartBookId,
        Long cartId
) {
}
