package com.nhnacademy.bookstorefront.main.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nhnacademy.bookstorefront.main.dto.book.CategorySimpleResponseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BookRegisterDto {
    private Long bookId;
    private String bookTitle;
    private String bookIndex;
    private String bookDescription;
    private LocalDate bookPubDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal bookPriceStandard;


    private String bookIsbn13;
    private String publisher;       // 출판사
    private String imageUrl;
    private List<CategoryDto> categories; // 카테고리 ID 리스트
    private List<String> authors;      // 작가 리스트

    @Data
    public static class CategoryDto {
        private Integer categoryId;
        private String categoryName;
    }

//    @Data
//    public static class AuthorDto {
//        private Integer authorId;
//        private String authorName;
//    }
}