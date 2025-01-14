package com.nhnacademy.bookstorefront.main.client;


import com.nhnacademy.bookstorefront.main.dto.*;
import com.nhnacademy.bookstorefront.main.dto.book.CategoryRegisterDto;
import com.nhnacademy.bookstorefront.main.dto.book.CategorySimpleResponseDto;
import com.nhnacademy.bookstorefront.main.dto.review.ReviewResponseDto;
import com.nhnacademy.bookstorefront.main.dto.book.*;
import com.nhnacademy.bookstorefront.main.dto.review.ReviewWithReviewImageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @GetMapping("/api/admin/selling-books")
    Page<AdminBookRegisterDto> adminGetBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    );

//    /**
//     * 관리자 도서관리에서 도서 수정 - 불러오기
//     * @param sellingBookId
//     * @param
//     * @return
//     */
//    @GetMapping("/api/admin/selling-books/{sellingBookId}")
//    AdminBookRegisterDto getUpdateForm(@PathVariable("sellingBookId") Long sellingBookId);

    /**
     * 관리자 도서 관리에서 도서 수정 - 수정후 데이터베이스 반영
     * @param sellingBookId
     * @param updateDto
     * @return
     */
    @PutMapping("/api/admin/selling-books/{sellingBookId}")
    void updateSellingBook(@PathVariable("sellingBookId") Long sellingBookId,
                           @RequestBody AdminBookRegisterDto updateDto);


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


    // 판매 책 등록
    @PostMapping("/api/admin/selling-books/selling-register")
    void registerSellingBooks(@RequestBody SellingBookRegisterDto sellingBookDto);

    // 판매 책 수정
    @PostMapping("/api/admin/selling-books/{sellingBookId}")
    SellingBookRegisterDto updateSellingBook(
            @PathVariable("sellingBookId") Long sellingBookId,
            @RequestBody SellingBookRegisterDto sellingBookDto);

    // 판매 책 목록 조회
    @GetMapping("/api/admin/selling-books/selling-list")
    Page<SellingBookRegisterDto> getSellingBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size);

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


    @PostMapping(value = "/api/reviews", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<ReviewResponseDto> createReview(@RequestPart("reviewRequestDto") String reviewRequestDto,
                                                   @RequestPart(value = "images", required = false) List<MultipartFile> images);

    @GetMapping("/api/order-product/by-selling-book/{sellingBookId}")
    ResponseEntity<Long> getOrderProductBySellingBookId(@PathVariable("sellingBookId") Long sellingBookId);

    @PostMapping("/api/tags")
    ResponseEntity<Void> saveTag(@RequestBody TagRegisterDto tagRegisterDto);

    @GetMapping("/api/tags")
    ResponseEntity<Page<TagResponseDto>> getAllTags(@RequestParam(required = false) String keyword,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size);

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


    @GetMapping("/api/book-tags/{bookId}")
    ResponseEntity<List<BookTagResponseDto>> getBookTagsByBookId(@PathVariable Long bookId);

    //sellingBookId에 해당하는 리뷰 가져오기
    @GetMapping("/api/books/{sellingBookId}/reviews")
    ResponseEntity<Page<ReviewWithReviewImageDto>> getReviewsByBookId(@PathVariable("sellingBookId") Long sellingBookId,
                                                                      @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "2") int size);

    //책 평점
    @GetMapping("/api/books/{sellingBookId}/reviews/avg")
    Double getAverageReview(@PathVariable("sellingBookId") Long sellingBookId);

    //리뷰 수정
    @PostMapping(value = "/api/reviews/{reviewId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Object> updateReview(@PathVariable("reviewId") Long reviewId,
                                        @RequestParam("score") Integer score,
                                        @RequestPart("content") String content,
                                        @RequestPart(value = "images", required = false) List<MultipartFile> images);

}
