package com.nhnacademy.bookstorefront.main.controller.admin;

import com.nhnacademy.bookstorefront.main.client.BookClient;
import com.nhnacademy.bookstorefront.main.dto.book.AuthorRegisterDto;
import com.nhnacademy.bookstorefront.main.dto.book.AuthorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * 작가 view html 반환
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminAuthorController {

    private final BookClient bookClient;

    @GetMapping("/admin/authors")
    public String adminAuthorList(
            Model model,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page, // 페이지 번호
            @RequestParam(defaultValue = "10") int size, // 페이지 크기
            @RequestParam(required = false) Long authorId,
            HttpServletRequest request) {

        if (authorId == null) {
            // 페이징된 작가 리스트 가져오기
            Page<AuthorResponseDto> authorResponseDtoPage = bookClient.getAllAuthors(keyword, page, size).getBody();

            // 모델에 페이지 데이터 추가
            model.addAttribute("authors", authorResponseDtoPage.getContent()); // 작가 목록
            model.addAttribute("keyword", keyword); // 검색어
            model.addAttribute("currentPage", page); // 현재 페이지
            model.addAttribute("totalPages", authorResponseDtoPage.getTotalPages()); // 전체 페이지 수
            model.addAttribute("totalElements", authorResponseDtoPage.getTotalElements()); // 전체 요소 수
            model.addAttribute("pageSize", size); // 페이지 크기

            return "admin/authors";
        } else {
            // 특정 작가 선택 시
            List<AuthorResponseDto> booksByAuthor = bookClient.getBooksByAuthor(authorId).getBody();
            String authorName = bookClient.getAuthorById(authorId).getBody();
            model.addAttribute("authorId", authorId);
            model.addAttribute("books", booksByAuthor);
            model.addAttribute("currentUri", request.getRequestURI());
            model.addAttribute("authorName", authorName);

            return "admin/authors";
        }
    }

    @PostMapping("/admin/authors")
    public String saveAuthor(@RequestParam String authorName) {
        AuthorRegisterDto authorRegisterDto = new AuthorRegisterDto();
        authorRegisterDto.setAuthorName(authorName);
        bookClient.saveAuthor(authorRegisterDto);
        return "redirect:/admin/authors";
    }

    @RequestMapping(value = "/admin/authors/{author-id}", method = RequestMethod.POST)
    public String deleteAuthor(@PathVariable("author-id") Long authorId,
                               @RequestParam("_method") String method) {
        if ("delete".equals(method)) {
            bookClient.deleteAuthor(authorId);
        }
        return "redirect:/admin/authors";
    }

    @PostMapping("/admin/books/{bookId}/authors/{authorId}")
    public String bookAuthorSave(@PathVariable Long bookId, @PathVariable Long authorId, Model model) {
        bookClient.saveBookAuthor(bookId, authorId);
        return "redirect:/admin/authors?authorId=" + authorId;
    }

    @RequestMapping(value = "/admin/book/{book-id}/authors/{author-id}", method = RequestMethod.POST)
    public String deleteBookAuthor(@PathVariable(name = "author-id") Long authorId,
                                   @PathVariable(name = "book-id") Long bookId,
                                   @RequestParam("_method") String method, Model model) {
        if ("delete".equals(method)) {
            bookClient.deleteBookAuthor(bookId, authorId);
        }
        model.addAttribute("bookId", bookId);
        model.addAttribute("authorId", authorId);

        return "redirect:/admin/authors?authorId=" + authorId;
    }
}
