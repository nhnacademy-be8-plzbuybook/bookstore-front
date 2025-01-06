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
public class MemberGrade {

    private Long memberGradeId;

    private String memberGradeName;

    private BigDecimal conditionPrice;

    private LocalDateTime gradeChange;
}
