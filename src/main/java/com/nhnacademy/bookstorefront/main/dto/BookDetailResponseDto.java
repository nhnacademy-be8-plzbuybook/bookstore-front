package com.nhnacademy.bookstorefront.main.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDetailResponseDto {
    String bookTitle;
    String imageUrl; // 도서 이미지 URL

    // Getter 및 Setter
    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
