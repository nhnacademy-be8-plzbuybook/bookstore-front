package com.nhnacademy.bookstorefront.main.dto.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberPointResponseDto {
    private Long memberPointId;
    private String pointConditionName;
    private BigDecimal point;
    private LocalDateTime addDate;
    private LocalDateTime endDate;
    private LocalDateTime usingDate;
}