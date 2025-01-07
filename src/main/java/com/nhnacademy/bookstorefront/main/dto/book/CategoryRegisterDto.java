package com.nhnacademy.bookstorefront.main.dto.book;


import lombok.Data;

@Data
public class CategoryRegisterDto {

    private Long parentCategoryId;
    private String newCategoryName;

}
