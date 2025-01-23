package com.nhnacademy.bookstorefront.main.controller.book;

import com.nhnacademy.bookstorefront.main.client.BookClient;
import com.nhnacademy.bookstorefront.main.dto.book.AuthorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 작가 모달창으로 불러오는거 Json 사용,,
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@ResponseBody // JSON 응답으로 처리
public class AuthorController {

    private final BookClient bookClient;

    // 관리자가 도서 추가 버튼 누르면 보이는 페이지 = 이거는 잘돼
    @GetMapping("/api/admin/authors")
    public Page<AuthorResponseDto> getAuthors(@RequestParam int page,
                                              @RequestParam int size, HttpServletRequest request) {
        return bookClient.getAuthors(page, size);
//        return "admin/bookregister"; // 모달창으로 가야하는데
    }
}
