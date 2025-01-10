package com.nhnacademy.bookstorefront.main.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OrderCancelRequestDto {
    @NotBlank
    private String cancelReason;
}
