package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.AuthenticationClient;
import com.nhnacademy.bookstorefront.main.client.BookClient;
import com.nhnacademy.bookstorefront.main.dto.BookDetailResponseDto;
import com.nhnacademy.bookstorefront.main.dto.PagedResponse;
import com.nhnacademy.bookstorefront.main.dto.book.SellingBookResponseDto;
import com.nhnacademy.bookstorefront.main.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {
    private final BookClient bookClient;
    private final AuthenticationService authenticationService;
    private final AuthenticationClient authenticationClient;

    @GetMapping({"/index", "/"})
    public String index(
            @RequestParam(defaultValue = "0") int page,        // 페이지 번호
            @RequestParam(defaultValue = "16") int size,       // 한 페이지당 아이템 수
            @RequestParam(defaultValue = "sellingBookId") String sortBy, // 정렬 기준
            @RequestParam(defaultValue = "desc") String sortDir, // 정렬 방향
            Model model, HttpServletRequest request) {
        // Feign 클라이언트를 통해 페이징된 데이터 요청
        Page<SellingBookResponseDto> response = bookClient.getBooks(page, size, sortBy, sortDir).getBody();

        boolean isLoggedIn = authenticationService.isLoggedIn(request);
        String role = null;

        if(isLoggedIn) {
            String token = getTokenFromCookies(request);
            if(token != null) {
                role = authenticationClient.getRoleFromToken("Bearer " + token).getBody();
            }
        }


        model.addAttribute("books", response.getContent());
        model.addAttribute("currentPage", response.getNumber());
        model.addAttribute("totalPages", response.getTotalPages());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("pageSize", size);
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("role", role);

        return "index"; // index.html 렌더링
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
}
