package com.nhnacademy.bookstorefront.main.dto.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MemberSearchRequestDto {
    private Integer page;
    private Integer size;
}
