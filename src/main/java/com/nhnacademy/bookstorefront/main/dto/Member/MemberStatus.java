package com.nhnacademy.bookstorefront.main.dto.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberStatus {
    private Long memberStateId;
    private String memberStateName;
}
