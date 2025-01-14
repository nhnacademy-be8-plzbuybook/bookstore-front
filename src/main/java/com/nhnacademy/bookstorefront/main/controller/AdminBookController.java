package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.BookClient;
import com.nhnacademy.bookstorefront.main.dto.*;
import com.nhnacademy.bookstorefront.main.dto.book.CategorySimpleResponseDto;
import com.nhnacademy.bookstorefront.main.dto.AdminBookRegisterDto;
import com.nhnacademy.bookstorefront.main.dto.BookDetailResponseDto;
import com.nhnacademy.bookstorefront.main.dto.SellingBookRegisterDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        Page<AdminBookRegisterDto> books = bookClient.adminGetBooks(page, size);
        model.addAttribute("books", books.getContent());  // books.getContent()로 리스트만 전달
        model.addAttribute("currentPage", books.getNumber());
        model.addAttribute("totalPages", books.getTotalPages());
        return "admin/bookList";
    }




    // 관리자가 도서 추가 버튼 누르면 보이는 페이지 = 이거는 잘돼
    @GetMapping("/register")
    public String showRegisterPage() {
        return "admin/bookregister"; // 등록 페이지
    }


    // 도서 등록 처리
    @PostMapping("/register")
    public String registerSellingBook(@RequestBody @Valid BookRegisterDto bookRegisterDto) {
        log.info("Received Book Title: {}", bookRegisterDto.getBookTitle());
        log.info("Received Categories: {}", bookRegisterDto.getCategories());
        log.info("Received Authors: {}", bookRegisterDto.getAuthors());
        // 클라이언트를 통해 데이터 전송
        bookClient.registerSellingBook(bookRegisterDto);

        // 성공적으로 처리된 경우 리다이렉트
        return "redirect:/admin/selling-books";

    }

//    /**
//     * 도서 책 수정 폼 데이터 가져오기
//     * @param sellingBookId
//     * @param model
//     * @return
//     */
//    @GetMapping("/update/{sellingBookId}")
//    public String getUpdateForm(@PathVariable Long sellingBookId, Model model) {
//        AdminBookRegisterDto book = bookClient.getUpdateForm(sellingBookId);
//
//        if (book == null || book.getSellingBookId() == null) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Selling book not found.");
//        }
//
//        model.addAttribute("book", book);
//        return "admin/update";
//    }

    /**
     * 도서 수정 데이터 저장
     * @param sellingBookId
     * @param updateDto
     * @return
     */
    @PostMapping("/update/{sellingBookId}")
    public String updateBook(
            @PathVariable Long sellingBookId,
            @ModelAttribute @Valid AdminBookRegisterDto updateDto) {
        // 클라이언트를 통해 수정 API 호출
        bookClient.updateSellingBook(sellingBookId, updateDto);

        // 수정 완료 후 목록 페이지로 리다이렉트
        return "redirect:/admin/selling-books";
    }


    /**
     * //TODO 판매책 불러오는 get
     * 판매 책 목록 페이지 - 관리자 도서불러올때랑똑같은데 return 파일이름이 달라 ..
     * @param page
     * @param size
     * @param model
     * @return
     */
    @GetMapping("/selling-list")
    public String getSellingBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        Page<SellingBookRegisterDto> sellingBooks = bookClient.getSellingBooks(page, size);

        sellingBooks.getContent().forEach(book -> log.debug("SellingBookRegisterDto: {}", book));


        model.addAttribute("sellingBooks", sellingBooks.getContent());
        model.addAttribute("currentPage", sellingBooks.getNumber());
        model.addAttribute("totalPages", sellingBooks.getTotalPages());
        return "admin/sellingbook"; // HTML 파일 이름
    }



    //TODO 판매책 수정후 저장하는 PUT
    @PostMapping("/{sellingBookId}")
    public String updateSellingBook(
            @PathVariable Long sellingBookId,
            @ModelAttribute @Valid SellingBookRegisterDto updateDto) {
        // 클라이언트를 통해 수정 API 호출
        bookClient.updateSellingBook(sellingBookId, updateDto);

        // 수정 완료 후 목록 페이지로 리다이렉트
        return "redirect:/admin/selling-books/selling-list";
    }




    /**
     * 동기화버튼
     * @param queryType
     * @param searchTarget
     * @param start
     * @param maxResults
     * @return
     */
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
            return "redirect:/admin/selling-books";
        } catch (Exception e) {
            log.error("도서 동기화 중 오류 발생: {}", e.getMessage());
            // 실패 시 리다이렉트
            return "redirect:/admin/selling-books?error=true";
        }
    }

    @PostMapping("/sync/isbn")
    public String syncBooksByIsbns(@RequestParam String isbn) {
        try {
            // 쉼표로 구분된 ISBN 리스트를 파싱
            List<String> isbns = List.of(isbn.split(","));
            bookClient.syncBooksByIsbns(isbns);
            log.info("ISBN 동기화 성공: {}", isbns);

            // 성공 시 리다이렉트
            return "redirect:/admin/selling-books";
        } catch (Exception e) {
            log.error("ISBN 동기화 중 오류 발생: {}", e.getMessage());

            // 실패 시 리다이렉트
            return "redirect:/admin/selling-books?error=true";
        }
    }

}

