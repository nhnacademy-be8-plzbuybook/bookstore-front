package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.BookClient;
import com.nhnacademy.bookstorefront.main.dto.AdminSellingBookRegisterDto;
import com.nhnacademy.bookstorefront.main.dto.BookDetailResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/selling-books")
public class AdminBookController {

    private final BookClient bookClient;

    @Autowired
    public AdminBookController(BookClient bookClient) {
        this.bookClient = bookClient;
    }

    // 관리자용 도서 목록 (페이징 처리만)
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

    // 도서 등록 페이지
    @GetMapping("/register")
    public String registerBookPage() {
        return "admin/bookregister"; // templates/admin/bookRegister.html
    }

    // 도서 수정 페이지
    @GetMapping("/edit/{id}")
    public String editBookPage(@PathVariable Long id, Model model) {
        BookDetailResponseDto book = bookClient.getSellingBook(id);
        model.addAttribute("book", book);
        return "admin/bookEdit"; // templates/admin/bookEdit.html
    }

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

