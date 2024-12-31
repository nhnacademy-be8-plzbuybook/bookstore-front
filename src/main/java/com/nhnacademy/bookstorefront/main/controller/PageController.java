package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.AuthenticationClient;
import com.nhnacademy.bookstorefront.main.dto.BookDetailResponseDto;
import com.nhnacademy.bookstorefront.main.dto.Member.MemberModifyRequestDto;
import com.nhnacademy.bookstorefront.main.dto.mypage.MyPageDto;
import com.nhnacademy.bookstorefront.main.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final AuthenticationService authenticationService;
    private final AuthenticationClient authenticationClient;

    @GetMapping("/mypage")
    public String mypage(HttpServletRequest request, Model model) {
        boolean isLoggedIn = authenticationService.isLoggedIn(request);
        if (!isLoggedIn) {
            return "redirect:/login";
        }

        MyPageDto myPageDto = authenticationService.getMyPage();
        List<BookDetailResponseDto> books = authenticationClient.getBooks();

        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("member", myPageDto);
        model.addAttribute("books", books);

        return "member/mypage";
    }

    @PostMapping("/mypage/update")
    public String updateMember(MemberModifyRequestDto modifyRequestDto, Model model) {
        ResponseEntity<String> response = authenticationClient.updateMember(modifyRequestDto);

        if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("successMessage", "회원 정보가 성공적으로 수정되었습니다.");
        } else {
            model.addAttribute("errorMessage", "회원 정보 수정에 실패했습니다.");
        }

        return "redirect:/mypage";
    }
}
