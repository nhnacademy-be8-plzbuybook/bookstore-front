package com.nhnacademy.bookstorefront.main.client;

import com.nhnacademy.bookstorefront.main.dto.BookSearchPagedResponseDto;
import com.nhnacademy.bookstorefront.main.dto.Member.MemberModifyRequestDto;
import com.nhnacademy.bookstorefront.main.dto.book.BookSearchResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "GATEWAY", contextId = "bookClient")
public interface BookSearchClient {

//    @GetMapping("/api/books")
//    List<BookSearchResponseDto> searchBook(@RequestParam String searchKeyword);

    @GetMapping("/api/books")
    BookSearchPagedResponseDto searchBook(
            @RequestParam("searchKeyword") String searchKeyword,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    );


}
