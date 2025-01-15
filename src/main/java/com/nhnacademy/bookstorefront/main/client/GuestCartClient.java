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
                                         @RequestHeader("cart") String sessionId);

    @GetMapping("/api/bookstore/guests/carts") // 장바구니 전체 조회
    List<ReadCartBookResponse> getGuestCartBooks(@RequestHeader("cart") String sessionId);

    @PutMapping("/api/bookstore/guests/carts") // 장바구니 선택 수정
    Map<String, Long> updateGuestCartBook(@RequestParam Long cartId,
                                          @RequestParam int quantity,
                                          @RequestHeader("cart") String sessionId);

    @DeleteMapping("/api/bookstore/guests/carts") // 장바구니 선택 삭제
    void deleteGuestCartBook(@RequestParam Long cartId,
                                          @RequestHeader("cart") String sessionId);

    @DeleteMapping("/api/bookstore/guests/carts") // 장바구니 전체 비우기
    void deleteAllGuestCart(@RequestHeader("cart") String sessionId);

}
