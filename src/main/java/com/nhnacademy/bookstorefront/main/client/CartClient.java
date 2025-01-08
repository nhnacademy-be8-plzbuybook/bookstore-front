package com.nhnacademy.bookstorefront.main.client;


import com.nhnacademy.bookstorefront.main.dto.cart.request.CreateCartBookRequest;
import com.nhnacademy.bookstorefront.main.dto.cart.request.DeleteCartBookRequest;
import com.nhnacademy.bookstorefront.main.dto.cart.response.ReadCartBookResponse;
import com.nhnacademy.bookstorefront.main.dto.cart.request.UpdateCartBookRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "GATEWAY", contextId = "cartClient")
public interface CartClient {

    @GetMapping("/api/bookstore/carts/{cartBookId}")
    ResponseEntity<List<ReadCartBookResponse>> getCartBook(@PathVariable("cartBookId") Long cartBookId);

    @GetMapping("/api/bookstore/carts")
    ResponseEntity<List<ReadCartBookResponse>> getCartBooks();

    @PostMapping("/api/bookstore/carts")
    ResponseEntity<Long> createCartBook(@RequestBody CreateCartBookRequest createCartBookRequest);

    @PutMapping("/api/bookstore/carts")
    ResponseEntity<Long> updateCartBook(@RequestBody UpdateCartBookRequest updateCartBookRequest);

    @DeleteMapping("/api/bookstore/carts/{cartId}") // 유저에 해당하는 모든 장바구니 삭제.
    ResponseEntity<String> deleteCartBook(@PathVariable("cartId") Long cartId);

    @DeleteMapping("/api/bookstore/carts") // 유저가 가진 장바구니에서 해당 책 장바구니 삭제
    ResponseEntity<String> deleteAllCartBook(@RequestBody DeleteCartBookRequest deleteCartCartBookRequest);
}
