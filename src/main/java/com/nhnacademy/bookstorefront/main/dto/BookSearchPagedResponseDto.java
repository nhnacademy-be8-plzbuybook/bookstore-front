package com.nhnacademy.bookstorefront.main.dto;

import com.nhnacademy.bookstorefront.main.dto.book.BookSearchResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class BookSearchPagedResponseDto {
    private List<BookSearchResponseDto> content;  // 책 목록
    private int number;                          // 현재 페이지 번호
    private int totalPages;                      // 전체 페이지 수
    private long totalElements;                  // 전체 데이터 수

    // getters and setters
}
