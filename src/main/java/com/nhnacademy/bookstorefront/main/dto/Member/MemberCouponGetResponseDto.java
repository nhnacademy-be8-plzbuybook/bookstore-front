package com.nhnacademy.bookstorefront.main.dto.Member;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class MemberCouponGetResponseDto{
    String code; //쿠폰 코드 필수
    String status; //쿠폰 상태 (미사용, 사용완료, 기한만료, 취소) 필수
    LocalDateTime issuedAt; //발급 날짜 필수
    LocalDateTime expiredAt; //발급 만료 날짜 필수
    String name; //쿠폰 정책 이름 필수
    String saleType; //할인 타입(고정 할인, % 할인) 필수
    BigDecimal minimumAmount; //쿠폰 적용 최소 금액 (쿠폰을 쓰려면 최소 사용해야하는 금액) 필수
    BigDecimal discountLimit; //최대 할인 금액 필수
    Integer discountRatio; //할인율 필수
    boolean isStackable; //중복사용 가능 여부 필수
    String couponScope; //쿠폰 적용 범위 필수
}
