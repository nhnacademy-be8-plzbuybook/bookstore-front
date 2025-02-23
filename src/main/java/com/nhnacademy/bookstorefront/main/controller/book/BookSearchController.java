package com.nhnacademy.bookstorefront.main.controller.book;

import com.nhnacademy.bookstorefront.main.client.BookClient;
import com.nhnacademy.bookstorefront.main.client.BookSearchClient;
import com.nhnacademy.bookstorefront.main.dto.BookSearchPagedResponseDto;
import com.nhnacademy.bookstorefront.main.dto.book.BookInfoResponseDto;
import com.nhnacademy.bookstorefront.main.dto.book.CategoryResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BookSearchController {

    private final BookSearchClient bookSearchClient;
    private final BookClient bookClient;


    @GetMapping("/search/books")
    public String searchBook(Model model,
                             @RequestParam(defaultValue = "15", required = false) String searchKeyword,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "14")int size,
                             @RequestParam(required = false, name="category-id") Long categoryId, HttpServletRequest request) {

        if(categoryId != null) {

            Page<BookInfoResponseDto> responseDto = bookClient.searchBooksByCategory(categoryId,page,size).getBody();
            model.addAttribute("searchResult", Objects.requireNonNull(responseDto).getContent());
            model.addAttribute("totalPages", responseDto.getTotalPages());
            model.addAttribute("categoryId", categoryId);

        } else {


            // API 호출 (ResponseEntity로 반환)
            BookSearchPagedResponseDto response = bookSearchClient.searchBook(searchKeyword, page, size);
            // 모델에 검색어와 결과 및 페이징 정보를 추가
            model.addAttribute("searchKeyword", searchKeyword);
            model.addAttribute("searchResult", Objects.requireNonNull(response).getContent()); // 책 목록
            model.addAttribute("totalPages", response.getTotalPages()); // 전체 페이지 수

        }
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize",size);
        return "search/searchResult";


    }

    @GetMapping("/getChildrenCategories")
    public String getChildrenCategories(Model model, @RequestParam Long parentId, @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "15")int size, HttpServletRequest request) {
        // BookClient를 사용하여 자식 카테고리 가져오기
        ResponseEntity<Page<CategoryResponseDto>> response = bookClient.getCategory(parentId,page,size);

        // 받은 데이터를 모델에 추가
        model.addAttribute("categories", response.getBody());
        model.addAttribute("totalPages", Objects.requireNonNull(response.getBody()).getTotalPages()); // 전체 페이지 수
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize",size);
        model.addAttribute("parentId", parentId);



        return "categories"; // 해당 데이터를 이용해 뷰에 렌더링
    }

}
