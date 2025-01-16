package com.nhnacademy.bookstorefront.main.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderProductCancelRequestDto {
    @NotNull
    private Long orderProductId;

    @NotNull
    @Min(1)
    private Integer quantity;
}
