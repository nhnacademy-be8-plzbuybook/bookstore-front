package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.BookClient;
import com.nhnacademy.bookstorefront.main.dto.*;

import com.nhnacademy.bookstorefront.main.dto.book.BookResponseDto;
import com.nhnacademy.bookstorefront.main.dto.book.CategorySimpleResponseDto;
import com.nhnacademy.bookstorefront.main.dto.AdminBookRegisterDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/books")
public class AdminBookController {

    private final BookClient bookClient;

    @Autowired
    public AdminBookController(BookClient bookClient) {
        this.bookClient = bookClient;
    }

    @GetMapping
    public String adminGetBooks(
            @RequestParam(defaultValue = "0" , required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size,
            @RequestParam(required = false) String bookType, // 책 종류 파라미터
            Model model) {


        if ("non-selling".equals(bookType)) {
            Page<BookResponseDto> books = bookClient.getBooksNotInSellingBooks(page, size).getBody();
            model.addAttribute("books", books.getContent());
            model.addAttribute("totalPages", books.getTotalPages());

        } else {
            // 기본 도서 API 호출
            Page<AdminBookRegisterDto> books = bookClient.adminGetBooks(page, size);
            model.addAttribute("books", books.getContent());
            model.addAttribute("totalPages", books.getTotalPages());
        }
        model.addAttribute("bookType", bookType);
        model.addAttribute("currentPage", page);
        return "admin/bookList";
    }



    @RequestMapping(value = "/{bookId}", method = RequestMethod.POST)
    public String deleteBook(@PathVariable("bookId") Long bookId,
                            @RequestParam("_method") String method, @RequestParam(required = false) String bookType) {
        if ("delete".equals(method)) {
            bookClient.deleteBook(bookId);
        }
        return "redirect:/admin/books?bookType=" + bookType;
    }




    // 관리자가 도서 추가 버튼 누르면 보이는 페이지 = 이거는 잘돼
    @GetMapping("/register")
    public String showRegisterPage() {
        return "admin/bookRegister"; // 등록 페이지
    }


    // 도서 등록 처리
    @PostMapping("/register")
    public String registerBook(@RequestBody @Valid BookRegisterDto bookRegisterDto) {
        log.info("Received Book Title: {}", bookRegisterDto.getBookTitle());
        log.info("Received Categories: {}", bookRegisterDto.getCategories());
        log.info("Received Authors: {}", bookRegisterDto.getAuthors());
        // 클라이언트를 통해 데이터 전송
        bookClient.registerBook(bookRegisterDto);

        // 성공적으로 처리된 경우 리다이렉트
        return "redirect:/admin/books";

    }

    // 관리자가 도서 수정 버튼 누르면 보이는 페이지
    @GetMapping("/update/{bookId}")
    public String showUpdatePage(@PathVariable(name="bookId") Long bookId
                                ,Model model) {

        BookRegisterDto bookData = bookClient.showUpdatePage(bookId);
        model.addAttribute("bookId", bookId);
        model.addAttribute("bookData", bookData);
        return "admin/bookUpdate"; // 수정 페이지
    }


    //     * 도서 수정 데이터 저장

    //@RequestMapping(value = "/update/{bookId}", method = RequestMethod.POST)
//    @RequestMapping(value = "/update/{bookId}", method = RequestMethod.PUT)
//    @PutMapping("/{bookId}")
    @PostMapping("/update")
    public String updateBook(
            @RequestBody @Valid BookRegisterDto updateDto) {
        bookClient.updateBook(updateDto);

        return "redirect:/admin/books";
}




    /**
     * 동기화버튼
     * @param queryType
     * @param searchTarget
     * @param start
     * @param maxResults
     * @return
     */
    //도서
    @PostMapping("/sync")
    public String syncBooks(
            @RequestParam("queryType") String queryType,
            @RequestParam("searchTarget") String searchTarget,
            @RequestParam("start") int start,
            @RequestParam("maxResults") int maxResults
    ) {
        try {
            log.info("Received sync request: queryType={}, searchTarget={}, start={}, maxResults={}",
                    queryType, searchTarget, start, maxResults);

            bookClient.syncBooksFromListApi(queryType, searchTarget, start, maxResults);
            // 성공 시 리다이렉트
            return "redirect:/admin/books";
        } catch (Exception e) {
            log.error("도서 동기화 중 오류 발생: {}", e.getMessage());
            // 실패 시 리다이렉트
            return "redirect:/admin/books?error=true";
        }
    }
    //도서
    @PostMapping("/sync/isbn")
    public String syncBooksByIsbns(@RequestParam String isbn) {
        try {
            // 쉼표로 구분된 ISBN 리스트를 파싱
            List<String> isbns = List.of(isbn.split(","));
            bookClient.syncBooksByIsbns(isbns);
            log.info("ISBN 동기화 성공: {}", isbns);

            // 성공 시 리다이렉트
            return "redirect:/admin/books";
        } catch (Exception e) {
            log.error("ISBN 동기화 중 오류 발생: {}", e.getMessage());

            // 실패 시 리다이렉트
            return "redirect:/admin/books?error=true";
        }
    }

}

