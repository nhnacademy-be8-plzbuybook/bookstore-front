//package com.nhnacademy.bookstorefront.main.client;
//
//import com.nhnacademy.bookstorefront.main.dto.point.PointConditionRequestDto;
//import com.nhnacademy.bookstorefront.main.dto.point.PointConditionResponseDto;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import java.util.List;
//
//@FeignClient(name = "GATEWAY", contextId = "pointClient")
//public interface PointClient {
//
//    @PostMapping("/api/points-conditions")
//    ResponseEntity<PointConditionResponseDto> creatPointCondition(@RequestBody PointConditionRequestDto pointConditionRequestDto);
//
//    @GetMapping("/api/points-conditions")
//    ResponseEntity<List<PointConditionResponseDto>> getAllPointConditions();
//
//    @PostMapping("/points-conditions/{id}")
//    ResponseEntity<PointConditionResponseDto> updatePointCondition(@PathVariable Long id, @RequestBody PointConditionRequestDto pointConditionRequestDto);
//
//    // 삭제(비활성화)
//
//
//}
