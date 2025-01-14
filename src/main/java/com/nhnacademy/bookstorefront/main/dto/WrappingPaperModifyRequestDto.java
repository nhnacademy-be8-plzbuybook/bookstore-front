package com.nhnacademy.bookstorefront.main.dto;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class WrappingPaperModifyRequestDto {
    private String name;
    private BigDecimal price;
    private Long stock;
    @Nullable
    private MultipartFile imageFile;
}
