package com.nhnacademy.bookstorefront.main.dto.order;

import com.nhnacademy.bookstorefront.main.dto.BookDetailResponseDto;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OrderReceiptProduct {
    private String imageUrl;
    private String bookTitle;
    private BigDecimal price;
    private int quantity;

    public OrderReceiptProduct(BookDetailResponseDto bookDetail, int quantity) {
        this.imageUrl = bookDetail.getImageUrl();
        this.bookTitle = bookDetail.getBookTitle();
        this.price = bookDetail.getSellingPrice();
        this.quantity = quantity;
    }
}
