package com.nhnacademy.bookstorefront.main.dto.order;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderProductStatusPatchRequestDto {
    @NotBlank
    private String status;
}
