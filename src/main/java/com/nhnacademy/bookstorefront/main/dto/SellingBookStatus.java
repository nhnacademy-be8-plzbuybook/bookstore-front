package com.nhnacademy.bookstorefront.main.dto;

public enum SellingBookStatus {
    SELLING,    // 판매중
    SELLEND,    // 판매 종료
    DELETEBOOK; // 삭제된 책

    // 기본값을 반환하는 안전한 valueOf 메서드 추가
    public static SellingBookStatus safeValueOf(String status) {
        try {
            return SellingBookStatus.valueOf(status);  // 유효한 Enum 값이면 그대로 반환
        } catch (IllegalArgumentException | NullPointerException e) {
            return SellingBookStatus.SELLING;  // 잘못된 값이나 null일 경우 기본값을 반환
        }
    }
}