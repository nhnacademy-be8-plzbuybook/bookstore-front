package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.AuthenticationClient;
import com.nhnacademy.bookstorefront.main.client.BookClient;
import com.nhnacademy.bookstorefront.main.dto.BookDetailResponseDto;
import com.nhnacademy.bookstorefront.main.dto.cart.request.CreateCartBookRequest;
import com.nhnacademy.bookstorefront.main.dto.cart.request.DeleteCartBookRequest;
import com.nhnacademy.bookstorefront.main.dto.cart.request.UpdateCartBookRequest;
import com.nhnacademy.bookstorefront.main.dto.cart.response.ReadCartBookResponse;
import com.nhnacademy.bookstorefront.main.service.AuthenticationService;
import com.nhnacademy.bookstorefront.main.service.CartService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CartController {

    private final CartService cartService;
    private final AuthenticationService authenticationService;
    private final AuthenticationClient authenticationClient;

    public CartController(CartService cartService,
                          AuthenticationService authenticationService,
                          AuthenticationClient authenticationClient) {
        this.cartService = cartService;
        this.authenticationService = authenticationService;
        this.authenticationClient = authenticationClient;
    }

    @PostMapping("/carts")
    @ResponseBody // AJAX 요청에 대한 응답을 JSON 형식으로 반환
    public ResponseEntity<String> addToCart(@RequestBody CreateCartBookRequest createCartBookRequest) {
        try {
            cartService.createCart(createCartBookRequest);
            return ResponseEntity.ok("장바구니에 상품이 추가되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("장바구니에 상품 추가 실패");
        }

    }


    @GetMapping("/carts")
    public String getCart(Model model, HttpServletRequest httpServletRequest) {
        try{
            List<ReadCartBookResponse> readCartBookResponses = cartService.getCartBooks();
            boolean isLoggedIn = authenticationService.isLoggedIn(httpServletRequest);

            String role = null;

            if(isLoggedIn) {
                String token = getTokenFromCookies(httpServletRequest);
                if(token != null) {
                    role = authenticationClient.getRoleFromToken("Bearer " + token).getBody();
                }
            }


            model.addAttribute("cartItems", readCartBookResponses);
            model.addAttribute("isLoggedIn", isLoggedIn);
            model.addAttribute("role", role);

            return "cart/myCart";
        } catch(Exception e) {
            return "index";
        }
    }

    @PutMapping ("/carts")
    @ResponseBody
    public String updateCartBook(@RequestBody UpdateCartBookRequest updateCartBookRequest) {
        try{
            cartService.updateCartBook(updateCartBookRequest);
            return "redirect:cart/myCart";
        } catch(Exception e) {
            return "index";
        }
    }

    @DeleteMapping("/carts/{cartId}")
    @ResponseBody
    public String deleteCartBook(@PathVariable("cartId") Long cartId) {
        try{
            cartService.deleteCart(cartId);
            return "cart/myCart";
        } catch(Exception e) {
            return "index";
        }
    }

    @DeleteMapping("/carts")
    public String deleteAllCartBook(DeleteCartBookRequest deleteCartBookRequest) {
        try{
            cartService.deleteCartBook(deleteCartBookRequest);
            return "cart/myCart";
        } catch(Exception e) {
            return "index";
        }
    }

    private String getTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("accessToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }


}
