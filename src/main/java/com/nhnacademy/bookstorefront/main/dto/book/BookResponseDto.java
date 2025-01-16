package com.nhnacademy.bookstorefront.main.dto.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Getter
@Setter

public class BookResponseDto {
    private Long bookId;
    private String bookTitle;
    private BigDecimal StandardBookPrice;
    private String bookIsbn13;
    private LocalDate bookPubDate; // 출판일
    private String publisher; // 출판사


    public BookResponseDto() {

    }


}
