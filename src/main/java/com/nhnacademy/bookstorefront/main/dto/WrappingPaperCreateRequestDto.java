package com.nhnacademy.bookstorefront.main.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@NoArgsConstructor
@Setter
@Getter
public class WrappingPaperCreateRequestDto {
    private String name;
    private BigDecimal price;
    private Long stock;
    private MultipartFile imageFile;
}
