package com.nhnacademy.bookstorefront.main.dto;

import com.nhnacademy.bookstorefront.main.dto.book.BookSearchResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class AdminBookRegisterDto {
    private String bookTitle; // 제목
    private String bookIsbn13; // ISBN
    private String publisher; // 출판사 - 가져오기
    private LocalDate bookPubDate; // 출판일
    private String bookIndex; //목차
    private String bookDescription; // 설명
    private BigDecimal bookPriceStandard; //정가
    private BigDecimal sellingBookPrice; // 판매가 - 가져오기 판매책 테이블
    private Boolean sellingBookPackageable; // 포장 가능 여부 - 가져오기 판매책 테이블
    private Integer sellingBookStock; // 재고 - 가져오기 판매책 테이블
    private String imageUrl; // 이미지 URL
    private List<String> categories; // 카테고리 정보
    private List<String> authors; // 작가 정보 - 가져오기 작가 테이블

}
