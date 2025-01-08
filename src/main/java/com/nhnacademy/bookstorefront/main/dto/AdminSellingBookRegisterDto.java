package com.nhnacademy.bookstorefront.main.dto;

import com.nhnacademy.bookstorefront.main.dto.book.BookSearchResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
//예를 감싸는 클래수,,,,,,,,,
public class AdminSellingBookRegisterDto {
    private Long sellingBookId; // 판매도서 ID
    private String bookTitle; // 제목
    private LocalDate bookPubDate; // 출판일
    private String publisher; // 출판사
    private String bookIsbn13; // ISBN
    private BigDecimal sellingBookPrice; // 판매가
    private Boolean sellingBookPackageable; // 포장 가능 여부
    private Integer sellingBookStock; // 재고
    private BookSearchResponseDto.SellingBookStatus sellingBookStatus; // 판매 상태
    private Long sellingBookViewCount; // 조회수
    private String imageUrl; // 이미지 URL
    private List<String> categories; // 카테고리 정보
    private List<String> authors; // 작가 정보

}
