package com.nhnacademy.bookstorefront.main.dto;

import java.time.LocalDate;

public class BookRegisterDto {
    private String bookTitle;
    private String bookIndex;
    private String bookDescription;
    private LocalDate bookPubDate;
    private int bookPriceStandard;
    private String bookIsbn13;
    private Long publisherId;
}