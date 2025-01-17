package com.nhnacademy.bookstorefront.main.controller.book;

import com.nhnacademy.bookstorefront.main.client.BookClient;
import com.nhnacademy.bookstorefront.main.dto.book.CategorySimpleResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
@ResponseBody // JSON 응답으로 처리
public class CategoryController {

    private final BookClient bookClient;

    // 관리자가 도서 추가 버튼 누르면 보이는 페이지 = 이거는 잘돼
    @GetMapping("/api/admin/categories")
    public Page<CategorySimpleResponseDto> getCategories(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {

        return bookClient.getCategories(page, size);
//        return "admin/bookregister"; //모달창으로 가야함
    }

}
