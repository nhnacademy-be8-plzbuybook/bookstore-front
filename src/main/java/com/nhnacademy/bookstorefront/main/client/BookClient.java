package com.nhnacademy.bookstorefront.main.client;


import com.nhnacademy.bookstorefront.main.dto.*;
import com.nhnacademy.bookstorefront.main.dto.book.CategoryRegisterDto;
import com.nhnacademy.bookstorefront.main.dto.book.CategorySimpleResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "GATEWAY", contextId = "bookclient")
public interface BookClient {

    // 사용자 기능
    @GetMapping("/api/selling-books")
    Page<BookDetailResponseDto> getBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "sellingBookId") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    );

    @GetMapping("/api/selling-books/{sellingBookId}")
    BookDetailResponseDto getSellingBook(@PathVariable("sellingBookId") Long sellingBookId);

    // 관리자용 도서 목록 조회 (페이징 처리만)
    @GetMapping("/api/selling-books")
    Page<AdminSellingBookRegisterDto> adminGetBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    );

    /**
     * 관리자 도서관리에서 도서 수정 api
     * @param sellingBookId
     * @param updateDto
     * @return
     */
    @PutMapping("/api/admin/selling-books/{sellingBookId}")
    AdminBookRegisterDto updateSellingBook(
            @PathVariable("sellingBookId") Long sellingBookId,
            @RequestBody AdminBookRegisterDto updateDto
    );


    /**
     * 관리자 도서관리에서 도서 삭제 api
     * @param sellingBookId
     */
    @DeleteMapping("/api/admin/selling-books/{sellingBookId}")
    void deleteSellingBook(@PathVariable("sellingBookId") Long sellingBookId);

    /**
     * 관리자 도서관리에서 도서 등록 api
     * @param registerDto
     * @return
     */
    @PostMapping("/api/admin/selling-books/register")
    AdminBookRegisterDto registerSellingBook(@RequestBody AdminBookRegisterDto registerDto);



    // TODO 2개
    // 최신 도서 동기화
    @PostMapping("/api/books/sync")
    ResponseEntity<Void> syncBooksFromListApi(
            @RequestParam("queryType") String queryType,
            @RequestParam("searchTarget") String searchTarget,
            @RequestParam("start") int start,
            @RequestParam("maxResults") int maxResults
    );

    // 알라딘 API를 통해 ISBN 리스트로 도서 동기화
    @PostMapping("/api/books/sync/isbn")
    ResponseEntity<Void> syncBooksByIsbns(@RequestBody List<String> sellingbookId);

    //카테고리 검색
    @GetMapping("/api/categories")
    ResponseEntity<Page<CategorySimpleResponseDto>> searchCategories(
            @RequestParam String keyword,
            @RequestParam int page,
            @RequestParam int size);
    @PostMapping("/api/categories")
    ResponseEntity<Void> saveCategory(@RequestBody CategoryRegisterDto categoryRegisterDto);

    @DeleteMapping("/api/categories/{categoryId}")
    ResponseEntity<Void> deleteCategory(@PathVariable("categoryId") Long categoryId);





}