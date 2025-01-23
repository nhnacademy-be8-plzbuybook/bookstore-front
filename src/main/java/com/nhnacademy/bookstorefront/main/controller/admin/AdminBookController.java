package com.nhnacademy.bookstorefront.main.controller.admin;

import com.nhnacademy.bookstorefront.main.client.BookClient;
import com.nhnacademy.bookstorefront.main.dto.AdminBookRegisterDto;
import com.nhnacademy.bookstorefront.main.dto.BookRegisterDto;
import com.nhnacademy.bookstorefront.main.dto.book.BookResponseDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size,
            @RequestParam(required = false) String bookType, // 책 종류 파라미터
            @RequestParam(required = false) String keyword,

            Model model) {


        if ("non-selling".equals(bookType)) {
            Page<BookResponseDto> books = bookClient.getBooksNotInSellingBooks(page, size).getBody();
            model.addAttribute("books", Objects.requireNonNull(books).getContent());
            model.addAttribute("totalPages", books.getTotalPages());

        } else {
            // 기본 도서 API 호출
            Page<AdminBookRegisterDto> books = bookClient.adminGetBooks(keyword,page, size);
            model.addAttribute("books", books.getContent());
            model.addAttribute("totalPages", books.getTotalPages());
            model.addAttribute("keyword", keyword); // 검색어
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
    public ResponseEntity<String> registerBook(@RequestBody @Valid BookRegisterDto bookRegisterDto) {
        try {
            log.info("Received Book Title: {}", bookRegisterDto.getBookTitle());
            log.info("Received Categories: {}", bookRegisterDto.getCategories());
            log.info("Received Authors: {}", bookRegisterDto.getAuthors());
            // 클라이언트를 통해 데이터 전송
            bookClient.registerBook(bookRegisterDto);
            return ResponseEntity.status(201).body(" 등록 성공 ");
        } catch ( Exception e){
            return ResponseEntity.status(500).body(" 등록 실패 ");
        }


        // 성공적으로 처리된 경우 리다이렉트
//        return "redirect:/admin/books";


    }

    // 관리자가 도서 수정 버튼 누르면 보이는 페이지
    @GetMapping("/update/{bookId}")
    public String showUpdatePage(@PathVariable(name = "bookId") Long bookId
            , Model model) {

        BookRegisterDto bookData = bookClient.showUpdatePage(bookId);
        model.addAttribute("bookId", bookId);
        model.addAttribute("bookData", bookData);
        return "admin/bookUpdate"; // 수정 페이지
    }


    //     * 도서 수정 데이터 저장

    @PostMapping("/update")
    public ResponseEntity<String> updateBook(
            @RequestBody @Valid BookRegisterDto updateDto) {
        try {
            bookClient.updateBook(updateDto);
            return ResponseEntity.status(204).body("수정 완료");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("수정 실패");
        }
    }


    /**
     * 동기화버튼
     *
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

