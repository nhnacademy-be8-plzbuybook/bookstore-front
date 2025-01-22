package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.BookClient;
import com.nhnacademy.bookstorefront.main.controller.admin.AdminController;
import com.nhnacademy.bookstorefront.main.dto.book.SellingBookResponseDto;
import com.nhnacademy.bookstorefront.main.service.CookieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {
    private final BookClient bookClient;
    private final CookieService cookieService;
    private final AdminController adminController;

    @GetMapping({"/index", "/"})
    public String index(
            @RequestParam(defaultValue = "0") int page,        // 페이지 번호
            @RequestParam(defaultValue = "14") int size,       // 한 페이지당 아이템 수
            @RequestParam(defaultValue = "sellingBookId") String sortBy, // 정렬 기준
            @RequestParam(defaultValue = "desc") String sortDir, // 정렬 방향
            Model model, HttpServletRequest request,HttpServletResponse httpServletResponse) {

        HttpSession session = request.getSession(true);
        String sessionId = session.getId();
        cookieService.addCookie(httpServletResponse, "cart", sessionId, 60*60);




        // Feign 클라이언트를 통해 페이징된 데이터 요청
        Page<SellingBookResponseDto> response = bookClient.getBooks(page, size, sortBy, sortDir).getBody();


        model.addAttribute("books", response.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", response.getTotalPages());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("pageSize", size);

        return "index"; // index.html 렌더링
    }

}
