package com.nhnacademy.bookstorefront.main.dto.book;

import lombok.*;

@Data
@Getter
@RequiredArgsConstructor
@Setter
@AllArgsConstructor
public class TagResponseDto {

    private Long tagId;
    private String tagName;
}
