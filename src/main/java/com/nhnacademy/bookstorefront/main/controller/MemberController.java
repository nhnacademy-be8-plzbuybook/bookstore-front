package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.dto.Member.MemberCreateRequestDto;
import com.nhnacademy.bookstorefront.main.dto.Member.MemberCreateResponseDto;
import com.nhnacademy.bookstorefront.main.service.MemberService;
import lombok.RequiredArgsConstructor;
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
}
