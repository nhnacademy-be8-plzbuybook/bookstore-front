package com.nhnacademy.bookstorefront.main.dto.order;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NonMemberOrderDetailAccessRequestDto {
    @NotBlank
    private String orderNumber;
    @NotBlank
    private String password;
}
