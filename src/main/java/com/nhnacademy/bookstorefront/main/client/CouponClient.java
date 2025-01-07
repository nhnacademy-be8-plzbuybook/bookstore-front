package com.nhnacademy.bookstorefront.main.client;

import com.nhnacademy.bookstorefront.main.dto.coupon.CouponCalculationRequestDto;
import com.nhnacademy.bookstorefront.main.dto.coupon.CouponCalculationResponseDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "GATEWAY", contextId = "couponClient")
public interface CouponClient {

    // 특정 쿠폰을 주문 상품에 적용하여 (쿠폰서버에서) 할인 계산
    @PostMapping("/api/member-coupons/member/{member-id}/coupon/{coupon-id}/calculate")
    ResponseEntity<CouponCalculationResponseDto> applyOrderProductCoupon(@PathVariable("member-id") Long memberId, @PathVariable("coupon-id") Long couponId, @RequestBody @Valid CouponCalculationRequestDto calculationRequestDto);
}
