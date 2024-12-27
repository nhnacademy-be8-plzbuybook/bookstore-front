package com.nhnacademy.bookstorefront.main.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BookDetailResponseDto {
    private Long bookId;             // 책 ID 책테이블
    private Long sellingBookId;      // 판매책 ID 판매책 테이블
    private String bookTitle;        // 책 제목
    private String bookIndex;        // 목차
    private String bookDescription;  // 설명
    private List<String> categories; // 카테고리 목록
    private List<String> authors;    // 작가 목록
    private String publisher;        // 출판사
    private LocalDate bookPubDate;   // 출판일
    private String bookIsbn13;       // ISBN
    private String imageUrl;         // 이미지 URL
    private BigDecimal bookPriceStandard; //정가

}
