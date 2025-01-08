package com.nhnacademy.bookstorefront.main.dto.cart.request;

import lombok.Builder;

@Builder
public record UpdateCartBookRequest(
        Long cartBookId,
        Long sellingBookId,
        int quantity) {}

