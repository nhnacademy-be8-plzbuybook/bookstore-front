package com.nhnacademy.bookstorefront.main.dto.point;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PointConditionRequestDto {
    private String name;
    private Integer conditionPoint;
    private BigDecimal conditionPercentage;
    private boolean status;


}
