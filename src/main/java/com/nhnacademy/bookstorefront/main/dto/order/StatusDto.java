package com.nhnacademy.bookstorefront.main.dto.order;

import com.nhnacademy.bookstorefront.main.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class StatusDto {
    private OrderStatus status;
}
