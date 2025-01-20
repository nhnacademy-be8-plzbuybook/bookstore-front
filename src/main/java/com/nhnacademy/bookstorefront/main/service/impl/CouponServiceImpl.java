package com.nhnacademy.bookstorefront.main.service.impl;

import com.nhnacademy.bookstorefront.common.exception.CouponException;
import com.nhnacademy.bookstorefront.main.client.CouponClient;
import com.nhnacademy.bookstorefront.main.dto.Member.MemberCouponGetResponseDto;
import com.nhnacademy.bookstorefront.main.dto.coupon.*;
import com.nhnacademy.bookstorefront.main.service.CouponService;
import com.nhnacademy.bookstorefront.main.service.MemberService;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Slf4j
@Service
public class CouponServiceImpl implements CouponService {
    private final MemberService memberService;
    private final CouponClient couponClient;

    // 쿠폰정책 생성
    public CouponPolicyResponseDto createCouponPolicy(CouponPolicySaveRequestDto couponPolicySaveRequestDto) {
        try {
            CouponPolicyResponseDto createCouponPolicy = couponClient.createCouponPolicy(couponPolicySaveRequestDto).getBody();

            if (createCouponPolicy == null) {
                throw new CouponException("쿠폰정책 생성 에러");
            }

            return createCouponPolicy;

        } catch (FeignException | CouponException e) {
            log.error("createCouponPolicy Feign Exception: {}", e.getMessage());
            throw new CouponException(e.getMessage());
        }
    }

    // 활성중인 쿠폰정책 조회
    public Page<CouponPolicyResponseDto> findActiveCouponPolicies(boolean active, int page, int pageSize) {
        try {
            Page<CouponPolicyResponseDto> couponPolicyResponseDto = couponClient.findActiveCouponPolicies(active, page, pageSize).getBody();

            if (couponPolicyResponseDto == null) {
                throw new CouponException("쿠폰정책 조회 에러");
            }

            return couponPolicyResponseDto;

        } catch (FeignException | CouponException e) {
            log.error("findActiveCouponPolicies Feign Exception: {}", e.getMessage());
            throw new CouponException(e.getMessage());
        }
    }

    // 쿠폰대상 생성
    public CouponTargetResponseDto createCouponTarget(CouponTargetSaveRequestDto couponTargetSaveRequestDto) {
        try {
            CouponTargetResponseDto createCouponTarget = couponClient.createCouponTarget(couponTargetSaveRequestDto).getBody();

            if (createCouponTarget == null) {
                throw new CouponException("쿠폰대상 생성 에러");
            }

            return createCouponTarget;

        } catch (FeignException | CouponException e) {
            log.error("createCouponTarget Feign Exception: {}", e.getMessage());
            throw new CouponException(e.getMessage());
        }
    }

    // 쿠폰 생성
    public CouponCreateResponseDto createCoupon(CouponCreateRequestDto createRequest) {
        try {
            CouponCreateResponseDto couponCreateResponseDto = couponClient.createCoupon(createRequest).getBody();

            if (couponCreateResponseDto == null) {
                throw new CouponException("쿠폰 생성 에러");
            }

            return couponCreateResponseDto;
        } catch (FeignException | CouponException e) {
            log.error("createCoupon Feign Exception: {}", e.getMessage());
            throw new CouponException(e.getMessage());
        }
    }

    // 쿠폰 목록 조회
    public Page<CouponResponseDto> getAllCoupons(int page, int pageSize) {
        try {
            Page<CouponResponseDto> couponResponseDto = couponClient.getAllCoupons(page, pageSize).getBody();

            if (couponResponseDto == null) {
                throw new CouponException("쿠폰 목록 조회 에러");
            }

            return couponResponseDto;
        } catch (FeignException | CouponException e) {
            log.error("getAllCoupons Feign Exception: {}", e.getMessage());
            throw new CouponException(e.getMessage());
        }
    }

    // 회원쿠폰 목록 조회
    public Page<MemberCouponResponseDto> getAllMemberCoupons(int page, int pageSize) {
        try {
            Page<MemberCouponResponseDto> getAllMemberCoupons = couponClient.getAllMemberCoupons(page, pageSize).getBody();

            if (getAllMemberCoupons == null) {
                throw new CouponException("회원쿠폰 목록 조회 에러");
            }

            return getAllMemberCoupons;
        } catch (FeignException | CouponException e) {
            log.error("getAllMemberCoupons Feign Exception: {}", e.getMessage());
            throw new CouponException(e.getMessage());
        }
    }

    // 주문금액 할인 계산
    public CouponCalculationResponseDto applyOrderProductCoupon(Long couponId, CouponCalculationRequestDto calculationRequestDto, HttpServletRequest request) {
        try {
            CouponCalculationResponseDto applyOrderProductCoupon = couponClient.applyOrderProductCoupon(couponId, calculationRequestDto).getBody();

            if (applyOrderProductCoupon == null) {
                throw new CouponException("주문금액 할인 계산 에러");
            }

            return applyOrderProductCoupon;
        } catch (FeignException | CouponException e) {
            log.error("프론트서버 - applyOrderProductCoupon Feign Exception: {}", e.getMessage());
            throw new CouponException(e.getMessage());
        }
    }

    // 회원 본인이 사용가능한 쿠폰목록 조회
    public Page<MemberCouponGetResponseDto> getUnusedMemberCouponsByMemberId(Long memberId, Pageable pageable) {
        try {
            Page<MemberCouponGetResponseDto> getUnusedMemberCouponsByMemberId = couponClient.getUnusedMemberCouponsByMemberId(memberId, pageable).getBody();

            if (getUnusedMemberCouponsByMemberId == null) {
                throw new CouponException("사용가능한 쿠폰목록 조회 에러");
            }

            return getUnusedMemberCouponsByMemberId;
        } catch (FeignException | CouponException e) {
            log.error("getUnusedMemberCouponsByMemberId Feign Exception: {}", e.getMessage());
            throw new CouponException(e.getMessage());
        }
    }

    // 쿠폰 ID에 해당하는 쿠폰 이력 목록 조회
    public Page<CouponHistoryResponseDto> getHistoryByCouponId(Long couponId, int page, int pageSize) {
        try {
            Page<CouponHistoryResponseDto> getHistoryByCouponId = couponClient.getHistoryByCouponId(couponId, page, pageSize).getBody();

            if (getHistoryByCouponId == null) {
                throw new CouponException("쿠폰이력 목록 조회 에러");
            }

            return getHistoryByCouponId;
        } catch (FeignException | CouponException e) {
            log.error("getHistoryByCouponId Feign Exception: {}", e.getMessage());
            throw new CouponException(e.getMessage());
        }
    }

    // 특정 기간의 쿠폰 이력 목록 조회
    public Page<CouponHistoryResponseDto> getHistoryDate(String startDate, String endDate, int page, int pageSize) {
        try {
            Page<CouponHistoryResponseDto> getHistoryDate = couponClient.getHistoryDate(startDate, endDate, page, pageSize).getBody();

            if (getHistoryDate == null) {
                throw new CouponException("특정기간 쿠폰이력 목록 조회 에러");
            }

            return getHistoryDate;
        } catch (FeignException | CouponException e) {
            log.error("getHistoryDate Feign Exception: {}", e.getMessage());
            throw new CouponException(e.getMessage());
        }
    }

    // 회원에게 쿠폰 발급
    public MemberCouponResponseDto createMemberCoupon(@RequestBody @Valid MemberCouponCreateRequestDto memberCouponCreateRequestDto) {
        try {
            MemberCouponResponseDto createMemberCoupon = couponClient.createMemberCoupon(memberCouponCreateRequestDto).getBody();

            if (createMemberCoupon == null) {
                throw new CouponException("회원쿠폰 발급 에러");
            }

            return createMemberCoupon;
        } catch (FeignException | CouponException e) {
            log.error("createMemberCoupon Feign Exception: {}", e.getMessage());
            throw new CouponException(e.getMessage());
        }
    }

    // 쿠폰 ID로 회원쿠폰 조회
    public Page<MemberCouponResponseDto> getMemberCouponsByCouponId(Long couponId, int page, int pageSize) {
        try {
            Page<MemberCouponResponseDto> getMemberCouponsByCouponId = couponClient.getMemberCouponsByCouponId(couponId, page, pageSize).getBody();

            if (getMemberCouponsByCouponId == null) {
                throw new CouponException("회원쿠폰 조회 에러");
            }

            return getMemberCouponsByCouponId;
        } catch (FeignException | CouponException e) {
            log.error("getMemberCouponsByCouponId Feign Exception: {}", e.getMessage());
            throw new CouponException(e.getMessage());
        }
    }


}
