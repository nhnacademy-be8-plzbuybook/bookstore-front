package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.AuthenticationClient;
import com.nhnacademy.bookstorefront.main.client.MemberClient;
import com.nhnacademy.bookstorefront.main.dto.Member.MemberModifyRequestDto;
import com.nhnacademy.bookstorefront.main.dto.Member.MemberSearchRequestDto;
import com.nhnacademy.bookstorefront.main.dto.Member.MemberSearchResponseDto;
import com.nhnacademy.bookstorefront.main.dto.Member.MemberStatus;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {

    private final MemberClient memberClient;
    private final AuthenticationClient authenticationClient;

    public AdminController(MemberClient memberClient, AuthenticationClient authenticationClient) {
        this.memberClient = memberClient;
        this.authenticationClient = authenticationClient;
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
//        List<MemberStatus> memberStatusList = memberClient.getAllMemberStatus();

        model.addAttribute("members", response.getBody().getContent());
        model.addAttribute("totalPages", response.getBody().getTotalPages());
        model.addAttribute("currentPage", response.getBody().getNumber());
//        model.addAttribute("memberStatusList", memberStatusList);

        return "admin/adminpage";
    }

//    @PostMapping("/adminpage/update")
//    public String updateMember(MemberModifyRequestDto modifyRequestDto, Model model) {
//        ResponseEntity<String> response = authenticationClient.updateMember(modifyRequestDto);
//
//        if (response.getStatusCode().is2xxSuccessful()) {
//            model.addAttribute("successMessage", "회원 정보가 성공적으로 수정되었습니다.");
//        } else {
//            model.addAttribute("errorMessage", "회원 정보 수정에 실패했습니다.");
//        }
//
//        return "redirect:/adminpage";
//    }
}
