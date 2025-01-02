package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.AuthenticationClient;
import com.nhnacademy.bookstorefront.main.dto.Member.MemberCreateRequestDto;
import com.nhnacademy.bookstorefront.main.dto.Member.MemberCreateResponseDto;
import com.nhnacademy.bookstorefront.main.dto.WithdrawStateRequestDto;
import com.nhnacademy.bookstorefront.main.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final AuthenticationClient authenticationClient;

    @GetMapping("/signup")
    public String signup(@RequestParam(value = "email", required = false) String email, Model model) {
        model.addAttribute("email", email);
        return "member/signup";
    }

    @PostMapping("/members/signup")
    public String signupProcess(@ModelAttribute MemberCreateRequestDto memberCreateRequestDto, Model model) {
        try {
            MemberCreateResponseDto responseDto = memberService.createMember(memberCreateRequestDto);

            model.addAttribute("message", responseDto);

            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "member/signup";
        }
    }

    @PostMapping("/mypage/withdrawal")
    public String withdrawMember(@RequestParam String email, Model model, HttpServletRequest request, HttpServletResponse response) {
        ResponseEntity<String> responseEntity = authenticationClient.withdrawState(new WithdrawStateRequestDto(email));

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("successMessage", "회원 탈퇴가 성공적으로 처리되었습니다.");

            // 로그아웃 처리 (세션 무효화 및 쿠키 삭제)
            request.getSession().invalidate(); // 세션 무효화

            Cookie cookie = new Cookie("accessToken", null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);

        } else {
            model.addAttribute("errorMessage", "회원 탈퇴에 실패했습니다.");
        }

        return "redirect:/index";
    }

}