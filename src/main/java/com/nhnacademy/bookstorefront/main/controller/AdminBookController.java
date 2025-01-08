package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.BookClient;
import com.nhnacademy.bookstorefront.main.dto.AdminBookRegisterDto;
import com.nhnacademy.bookstorefront.main.dto.AdminSellingBookRegisterDto;
import com.nhnacademy.bookstorefront.main.dto.BookDetailResponseDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/selling-books")
public class AdminBookController {

    private final BookClient bookClient;

    @Autowired
    public AdminBookController(BookClient bookClient) {
        this.bookClient = bookClient;
    }

    // 관리자용 도서 목록 (페이징 처리만) 리스트 보임
    @GetMapping
    public String adminGetBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        Page<AdminSellingBookRegisterDto> books = bookClient.adminGetBooks(page, size);
        model.addAttribute("books", books.getContent());  // books.getContent()로 리스트만 전달
        model.addAttribute("currentPage", books.getNumber());
        model.addAttribute("totalPages", books.getTotalPages());
        return "admin/bookList";
    }


    // 도서 상세 조회
    @GetMapping("/{id}")
    public String getBookDetails(@PathVariable Long id, Model model) {
        BookDetailResponseDto book = bookClient.getSellingBook(id);
        model.addAttribute("book", book);
        return "admin/bookDetails"; // templates/admin/bookDetails.html
    }



    // 관리자가 도서 추가 버튼 누르면 보이는 페이지 = 이거는 잘돼
    @GetMapping("/register")
    public String showRegisterPage() {
        return "admin/bookRegister"; // 등록 페이지
    }


    // 도서 등록 페이지
    @PostMapping("/register")
    public String registerSellingBook(@ModelAttribute @Valid AdminBookRegisterDto adminSellingBookRegisterDto) {
        log.debug("DTO received for registration: {}", adminSellingBookRegisterDto);

        // BookClient를 통해 데이터 전송
        bookClient.registerSellingBook(adminSellingBookRegisterDto);

        // 성공적으로 처리된 경우 리다이렉트
        return "redirect:/admin/selling-books";

    }

    /**
     * 관리자 도서 수정
     * @param sellingBookId
     * @param updateDto
     * @return
     */
    @PostMapping("/update/{sellingBookId}")
    public String updateBook(
            @PathVariable Long sellingBookId,
            @ModelAttribute @Valid AdminBookRegisterDto updateDto) {
        log.debug("DTO received for update: {}", updateDto);

        // BookClient를 통해 수정 API 호출
        bookClient.updateSellingBook(sellingBookId, updateDto);

        return "redirect:/admin/selling-books";
    }





    // TODO 동기화 버튼

    @PostMapping("/sync")
    public ResponseEntity<Void> syncBooks() {
        bookClient.syncBooksFromListApi("ItemNewAll", "Book", 3, 50);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sync/isbn")
    public ResponseEntity<Void> syncBooksByIsbns(@RequestParam String isbn) {
        // 쉼표로 구분된 ISBN 리스트를 파싱
        List<String> isbns = List.of(isbn.split(","));
        bookClient.syncBooksByIsbns(isbns);
        return ResponseEntity.ok().build();
    }
}

