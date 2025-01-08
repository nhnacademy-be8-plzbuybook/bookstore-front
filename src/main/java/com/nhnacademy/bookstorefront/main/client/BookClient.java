package com.nhnacademy.bookstorefront.main.client;


import com.nhnacademy.bookstorefront.main.dto.*;
import com.nhnacademy.bookstorefront.main.dto.book.*;
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

//    @PostMapping("/api/selling-books/like/{sellingBookId}")
//    ResponseEntity<Long> toggleLike(@PathVariable("sellingBookId") Long sellingBookId);

    // 관리자용 도서 목록 조회 (페이징 처리만)
    @GetMapping("/api/admin/selling-books")
    Page<AdminSellingBookRegisterDto> adminGetBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    );

    // 관리자 기능 도서 등록
    @PostMapping("/api/admin/selling-books")
    BookDetailResponseDto registerSellingBook(@RequestBody BookDetailResponseDto registerDto);

    @PutMapping("/api/admin/selling-books/{sellingBookId}")
    BookDetailResponseDto updateSellingBook(
            @PathVariable("sellingBookId") Long sellingBookId,
            @RequestBody BookDetailResponseDto updateDto
    );

    @DeleteMapping("/api/admin/selling-books/{sellingBookId}")
    void deleteSellingBook(@PathVariable("sellingBookId") Long sellingBookId);


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

    @PostMapping("/api/tags")
    ResponseEntity<Void> saveTag(@RequestBody TagRegisterDto tagRegisterDto);

    @GetMapping("/api/tags")
    ResponseEntity<List<TagResponseDto>> getAllTags(@RequestParam String keyword);

    @DeleteMapping("/api/tags/{tagId}")
    ResponseEntity<Void> deleteTag(@PathVariable("tagId") Long tagId);

    @GetMapping("/api/book-tags")
    ResponseEntity<List<BookTagResponseDto>> getAllBookTags(@RequestParam Long tagId);

    @PostMapping("/api/book-tags")
    ResponseEntity<Void> saveBookTag(@RequestParam Long bookId, @RequestParam Long tagId);

    @DeleteMapping("/api/book-tags")
    ResponseEntity<Void> deleteBookTag(@RequestParam Long bookId, @RequestParam Long tagId);

    @GetMapping("/api/tags/{tagId}")
    ResponseEntity<String> getTagNameByTagId(@PathVariable Long tagId);








    }