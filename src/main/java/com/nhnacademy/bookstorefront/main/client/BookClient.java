package com.nhnacademy.bookstorefront.main.client;


import com.nhnacademy.bookstorefront.main.dto.*;
import com.nhnacademy.bookstorefront.main.dto.Member.MemberAddressRequestDto;
import com.nhnacademy.bookstorefront.main.dto.Member.MemberAddressResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "GATEWAY", contextId = "bookclient")
public interface BookClient {


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
}