package com.nhnacademy.bookstorefront.main.dto;

import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@Getter
public class AuthorResponseDto {
    private Long authorId;
    private String authorName;
    }
