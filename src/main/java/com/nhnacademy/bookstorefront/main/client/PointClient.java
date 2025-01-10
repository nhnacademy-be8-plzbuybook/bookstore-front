package com.nhnacademy.bookstorefront.main.client;

import com.nhnacademy.bookstorefront.main.dto.Member.MemberPointResponseDto;
import com.nhnacademy.bookstorefront.main.dto.point.MemberPointUseRequestDto;
import com.nhnacademy.bookstorefront.main.dto.point.PointConditionRequestDto;
import com.nhnacademy.bookstorefront.main.dto.point.PointConditionResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "GATEWAY", contextId = "pointClient")
public interface PointClient {

    // 포인트 내역 조회
    @GetMapping("/api/members/{memberId}/points")
    List<MemberPointResponseDto> getMemberPoints(@PathVariable("memberId") Long memberId);

    // 포인트 조건 생성
    @PostMapping("/api/points/conditions")
    ResponseEntity<PointConditionResponseDto> createPointCondition(@RequestBody PointConditionRequestDto pointConditionRequestDto);

    // 포인트 조건 목록 조회
    @GetMapping("/api/points/conditions")
    ResponseEntity<List<PointConditionResponseDto>> getAllPointConditions();

    // 포인트 조건 수정
    @PostMapping("/api/points/conditions/{id}")
    ResponseEntity<PointConditionResponseDto> updatePointCondition(@PathVariable Long id, @RequestBody PointConditionRequestDto pointConditionRequestDto);

    // 사용 가능 포인트 조회 (주문서)
    @PostMapping("/api/points/members/available")
    ResponseEntity<Integer> getAvailablePoints();

    // 포인트 사용
    @PostMapping("/api/points/members/use")
    ResponseEntity<String> usedPoints(@RequestBody MemberPointUseRequestDto requestDto);


}