package com.nhnacademy.bookstorefront.main.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class OrderReturnRequestDto {
    @NotBlank
    private String reason;

    @NotNull
    @Min(1)
    private Integer quantity;

    @NotBlank
    private String trackingNumber;
}
