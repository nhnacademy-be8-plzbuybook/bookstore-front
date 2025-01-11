package com.nhnacademy.bookstorefront.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class AdminCouponPopup {

    @GetMapping("/admin/member-coupons/issue-popup")
    public String showMemberCouponIssuePopup() {
        return "admin/coupon/memberCouponPopup";
    }

    @GetMapping("/admin/member-coupons/inquiry-popup")
    public String showMemberCouponInquiryPopup() {
        return "admin/coupon/memberCouponInquiryPopup";
    }
}
