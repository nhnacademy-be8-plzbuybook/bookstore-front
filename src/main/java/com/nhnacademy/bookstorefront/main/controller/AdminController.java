package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.AuthenticationClient;
import com.nhnacademy.bookstorefront.main.client.MemberClient;
import com.nhnacademy.bookstorefront.main.dto.Member.*;
import com.nhnacademy.bookstorefront.main.dto.point.PointConditionRequestDto;
import com.nhnacademy.bookstorefront.main.dto.point.PointConditionResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    private final MemberClient memberClient;
//    private final PointClient pointClient;

    public AdminController(MemberClient memberClient) {
        this.memberClient = memberClient;
    }
//    public AdminController(PointClient pointClient) { this.pointClient = pointClient; }

    @GetMapping("/adminpage")
    public String adminPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        MemberSearchRequestDto memberSearchRequestDto = new MemberSearchRequestDto();
        memberSearchRequestDto.setPage(page);
        memberSearchRequestDto.setSize(size);


        ResponseEntity<Page<MemberSearchResponseDto>> response = memberClient.getMembers(memberSearchRequestDto);
        List<MemberStatus> memberStatusList = memberClient.getAllMemberStatus();
        List<MemberGrade> memberGradeList = memberClient.getAllMemberGrade();
        List<PointConditionResponseDto> pointConditions = response.getBody();
        model.addAttribute("pointConditions", pointConditions);


        model.addAttribute("members", response.getBody().getContent());
        model.addAttribute("totalPages", response.getBody().getTotalPages());
        model.addAttribute("currentPage", response.getBody().getNumber());
        model.addAttribute("memberStatusList", memberStatusList);
        model.addAttribute("memberGradeList", memberGradeList);

        return "admin/adminPage";
    }

    @PostMapping("/adminpage/update")
    public String updateMember(MemberModifyByAdminRequestDto memberModifyByAdminRequestDto) {
        memberClient.updateMember(memberModifyByAdminRequestDto);
        return "redirect:/adminpage";
    }

    // 포인트 조건 등록
    @PostMapping("/adminpage/points-conditions/create")
    public String createPointCondition(@ModelAttribute PointConditionRequestDto pointConditionRequestDto) {
        memberClient.createPointCondition(pointConditionRequestDto);
        return "redirect:/adminpage/points-conditions";
    }

//    // 포인트 조건 목록 조회
//    @GetMapping("/adminpage/points-conditions")
//    public String getPointConditions(Model model) {
//        ResponseEntity<List<PointConditionResponseDto>> response = memberClient.getAllPointConditions();
//        List<PointConditionResponseDto> pointConditions = response.getBody();
//        model.addAttribute("pointConditions", pointConditions);
//        return "admin/adminPage/points-conditions"; // 포인트 조건 관리 페이지로 이동
//    }

    // 포인트 조건 수정
    @PostMapping("/adminpage/points-conditions/update/{id}")
    public String updatePointCondition(@PathVariable Long id, @ModelAttribute PointConditionRequestDto pointConditionRequestDto) {
        memberClient.updatePointCondition(id, pointConditionRequestDto);
        return "redirect:/adminpage/points-conditions";
    }



}
