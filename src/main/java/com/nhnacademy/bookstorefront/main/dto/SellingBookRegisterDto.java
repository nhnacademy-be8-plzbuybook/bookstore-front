package com.nhnacademy.bookstorefront.main.dto;

import lombok.Data;

import java.math.BigDecimal;

// 관리자 판매책등록 탭 dto
@Data
public class SellingBookRegisterDto {
    private Long bookId; // 연관된 책 ID
    private Long sellingBookId; // 추가 필요
    private BigDecimal sellingBookPrice; //판매가
    private Boolean sellingBookPackageable; //선물포장가능여부
    private Integer sellingBookStock; //재고
    private String sellingBookStatus; //도서상태
    private Long sellingBookViewCount;//조회수
    private Boolean used; //중고여부
}
