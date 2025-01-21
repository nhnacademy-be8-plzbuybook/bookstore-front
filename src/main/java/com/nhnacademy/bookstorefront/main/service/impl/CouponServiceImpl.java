package com.nhnacademy.bookstorefront.main.service.impl;

import com.nhnacademy.bookstorefront.common.exception.CouponException;
import com.nhnacademy.bookstorefront.main.client.CouponClient;
import com.nhnacademy.bookstorefront.main.dto.Member.MemberCouponGetResponseDto;
import com.nhnacademy.bookstorefront.main.dto.coupon.*;
import com.nhnacademy.bookstorefront.main.service.CouponService;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Slf4j
@Service
public class CouponServiceImpl implements CouponService {
    private final CouponClient couponClient;

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

    public Page<CouponPolicyResponseDto> findAllCouponPolicies(@RequestParam int page, @RequestParam int size) {
        try {
            Page<CouponPolicyResponseDto> findAllCouponPolicies = couponClient.findAllCouponPolicies(page, size).getBody();

            if (findAllCouponPolicies == null) {
                throw new CouponException("쿠폰정책 전체 조회 에러");
            }

            return findAllCouponPolicies;

        } catch (FeignException | CouponException e) {
            log.error("findAllCouponPolicies Feign Exception: {}", e.getMessage());
            throw new CouponException(e.getMessage());
        }
    }

    public CouponPolicyResponseDto findById(@PathVariable("id") @Min(0) Long couponPolicyId) {
        try {
            CouponPolicyResponseDto findById = couponClient.findById(couponPolicyId).getBody();

            if (findById == null) {
                throw new CouponException("쿠폰정책 ID 로 조회 에러");
            }

            return findById;
        } catch (FeignException | CouponException e) {
            log.error("findById Feign Exception: {}", e.getMessage());
            throw new CouponException(e.getMessage());
        }
    }

    public CouponPolicyResponseDto findByName(@RequestParam("name") @NotBlank String name) {
        try {
            CouponPolicyResponseDto findByName = couponClient.findByName(name).getBody();

            if (findByName == null) {
                throw new CouponException("쿠폰정책 이름 조회 에러");
            }

            return findByName;
        } catch (FeignException | CouponException e) {
            log.error("findByName Feign Exception: {}", e.getMessage());
            throw new CouponException(e.getMessage());
        }
    }

    public CouponPolicyResponseDto findCouponPolicyByCouponId(@PathVariable("coupon-id") @Min(0) Long couponId) {
        try {
            CouponPolicyResponseDto findCouponPolicyByCouponId = couponClient.findCouponPolicyByCouponId(couponId).getBody();

            if (findCouponPolicyByCouponId == null) {
                throw new CouponException("쿠폰 ID 로 쿠폰정책 조회 에러");
            }

            return findCouponPolicyByCouponId;
        } catch (FeignException | CouponException e) {
            log.error("findCouponPolicyByCouponId Feign Exception: {}", e.getMessage());
            throw new CouponException(e.getMessage());
        }
    }

    public String addCouponTargets(@PathVariable("policy-id") @Min(0) Long policyId, @RequestBody @Valid Long ctTargetId) {
        try {
            String addCouponTargets = couponClient.addCouponTargets(policyId, ctTargetId).getBody();

            if (addCouponTargets == null) {
                throw new CouponException("쿠폰정책에 쿠폰대상 추가 에러");
            }

            return addCouponTargets;
        } catch (FeignException | CouponException e) {
            log.error("addCouponTargets Feign Exception: {}", e.getMessage());
            throw new CouponException(e.getMessage());
        }
    }

    public Page<CouponTargetGetResponseDto> getCouponTargetsByPolicy(@RequestParam("policy-id") @Min(0) Long policyId,
                                                                     @RequestParam(defaultValue = "0") @Min(0) int page,
                                                                     @RequestParam(defaultValue = "10") @Min(1) int pageSize) {
        try {
            Page<CouponTargetGetResponseDto> couponTargetGetResponseDto = couponClient.getCouponTargetsByPolicy(policyId, page, pageSize).getBody();

            if (couponTargetGetResponseDto == null) {
                throw new CouponException("쿠폰정책에 속하는 쿠폰대상 목록 조회 에러");
            }

            return couponTargetGetResponseDto;
        } catch (FeignException | CouponException e) {
            log.error("getCouponTargetsByPolicy Feign Exception: {}", e.getMessage());
            throw new CouponException(e.getMessage());
        }
    }

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

    public Page<CouponResponseDto> getCouponsByPolicies(@PathVariable("policy-id") @Min(0) @NotNull Long policyId,
                                                        @RequestParam(defaultValue = "0") @Min(0) int page,
                                                        @RequestParam(defaultValue = "10") @Min(1) int pageSize) {
        try {
            Page<CouponResponseDto> getCouponsByPolicies = couponClient.getCouponsByPolicies(policyId, page, pageSize).getBody();

            if (getCouponsByPolicies == null) {
                throw new CouponException("특정 쿠폰정책에 속한 쿠폰 조회 에러");
            }

            return getCouponsByPolicies;
        } catch (FeignException | CouponException e) {
            log.error("getCouponsByPolicies Feign Exception: {}", e.getMessage());
            throw new CouponException(e.getMessage());
        }
    }

    public String useCoupon(@PathVariable("coupon-id") @Min(0) Long couponId) {
        try {
            String useCoupon = couponClient.useCoupon(couponId).getBody();

            if (useCoupon == null) {
                throw new CouponException("쿠폰사용 에러");
            }

            return useCoupon;
        } catch (FeignException | CouponException e) {
            log.error("useCoupon Feign Exception: {}", e.getMessage());
            throw new CouponException(e.getMessage());
        }
    }

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

}
