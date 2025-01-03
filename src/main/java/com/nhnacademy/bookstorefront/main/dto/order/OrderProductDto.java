package com.nhnacademy.bookstorefront.main.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
public class OrderProductDto {
    private String imageUrl;
    private String bookTitle;
    private Long bookId;
    private int quantity;
    private BigDecimal price;
    private String status;
    private OrderProductWrapping orderProductWrapping;
}

@NoArgsConstructor
@Getter
class OrderProductWrapping {
    String name;
    int quantity;
    BigDecimal price;
}
