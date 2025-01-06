package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.AuthenticationClient;
import com.nhnacademy.bookstorefront.main.client.MemberClient;
import com.nhnacademy.bookstorefront.main.dto.Member.*;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    private final MemberClient memberClient;

    public AdminController(MemberClient memberClient) {
        this.memberClient = memberClient;
    }

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


}
