package com.nhnacademy.bookstorefront.main.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class WrappingPaperDto {
    private final Long id;
    private final String name;
    private final BigDecimal price;
    private final Long stock;
    private final String imagePath;
    private final LocalDateTime createdAt;
}
