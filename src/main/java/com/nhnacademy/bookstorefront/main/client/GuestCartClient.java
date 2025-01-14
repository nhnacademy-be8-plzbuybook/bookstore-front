package com.nhnacademy.bookstorefront.main.client;

import com.nhnacademy.bookstorefront.main.dto.cart.request.CreateCartBookRequest;
import com.nhnacademy.bookstorefront.main.dto.cart.response.ReadCartBookResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "GATEWAY", contextId = "guestCartClient")
public interface GuestCartClient {


    @PostMapping("/api/bookstore/guests/carts") // 장바구니 등록
    Map<String, Long> createGuestCartBook(CreateCartBookRequest createCartBookRequest,
                                         @RequestHeader("Session-Id") String sessionId);

    @GetMapping("/api/bookstore/guests/carts") // 장바구니 전체 조회
    List<ReadCartBookResponse> getGuestCartBooks(@RequestHeader("Session-Id") String sessionId);

    @PutMapping("/api/bookstore/guests/carts/{cartId}") // 장바구니 선택 수정
    Map<String, Long> updateGuestCartBook(@PathVariable("cartId") Long cartId,
                                          @RequestParam int quantity,
                                          @RequestHeader("Session-Id") String sessionId);

    @DeleteMapping("/api/bookstore/guests/carts/{cartId}") // 장바구니 선택 삭제
    Map<String, Long> deleteGuestCartBook(@PathVariable("cartId") Long cartId,
                                          @RequestHeader("Session-Id") String sessionId);

    @DeleteMapping("/api/bookstore/guests/carts") // 장바구니 전체 비우기
    void deleteAllGuestCart(@RequestHeader("Session-Id") String sessionId);

}
