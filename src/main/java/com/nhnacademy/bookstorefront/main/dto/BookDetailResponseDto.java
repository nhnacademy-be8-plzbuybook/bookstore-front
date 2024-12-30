package com.nhnacademy.bookstorefront.main.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BookDetailResponseDto {
    private Long bookId;
    private Long sellingBookId;
    private String bookTitle;
    private String bookIndex;
    private String bookDescription;
    private String bookPubDate;
    private BigDecimal bookPriceStandard;
    private BigDecimal sellingPrice;
    private String bookIsbn13;
    private String publisher;
    private String imageUrl;
    private List<String> categories;
    private List<String> authorName;
    private String status;
    private Long likeCount; // null 허용
}
