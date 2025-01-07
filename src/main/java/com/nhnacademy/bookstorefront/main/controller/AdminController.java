package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.AuthenticationClient;
import com.nhnacademy.bookstorefront.main.client.MemberClient;
import com.nhnacademy.bookstorefront.main.dto.Member.*;
import com.nhnacademy.bookstorefront.main.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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
    private final AuthenticationService authenticationService;
    private final AuthenticationClient authenticationClient;

    public AdminController(MemberClient memberClient, AuthenticationService authenticationService, AuthenticationClient authenticationClient) {
        this.memberClient = memberClient;
        this.authenticationService = authenticationService;
        this.authenticationClient = authenticationClient;
    }

    @GetMapping("/adminpage")
    public String adminPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model,
            HttpServletRequest request) {
        MemberSearchRequestDto memberSearchRequestDto = new MemberSearchRequestDto();
        memberSearchRequestDto.setPage(page);
        memberSearchRequestDto.setSize(size);

        String token = getTokenFromCookies(request);
        if (token == null || !isAdmin(token)) {
            return "error/403";
        }


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

    private String getTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("accessToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private boolean isAdmin(String token) {
        String role = authenticationClient.getRoleFromToken("Bearer " + token).getBody();
        return "ADMIN".equals(role);
    }

}
