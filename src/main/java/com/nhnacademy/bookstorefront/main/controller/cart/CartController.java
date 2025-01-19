package com.nhnacademy.bookstorefront.main.controller.cart;

import com.nhnacademy.bookstorefront.main.dto.cart.request.CreateCartBookRequest;
import com.nhnacademy.bookstorefront.main.dto.cart.request.DeleteCartBookRequest;
import com.nhnacademy.bookstorefront.main.dto.cart.request.UpdateCartBookRequest;
import com.nhnacademy.bookstorefront.main.dto.cart.response.ReadCartBookResponse;
import com.nhnacademy.bookstorefront.main.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
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
        try {
            List<ReadCartBookResponse> readCartBookResponses = cartService.getCartBooks();
            model.addAttribute("cartItems", readCartBookResponses);
            return "cart/myCart";
        } catch (Exception e) {
            return "index";
        }
    }

    @PutMapping("/carts")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateCartBook(@RequestBody UpdateCartBookRequest updateCartBookRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            cartService.updateCartBook(updateCartBookRequest);
            response.put("success", true);
            response.put("message", "장바구니가 수정되었습니다.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "장바구니 수정 실패");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/carts/{cartId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteAllCartBook(@PathVariable("cartId") Long cartId, Model model) {
        model.addAttribute("cartId", cartId);
        Map<String, Object> response = new HashMap<>();
        try {
            cartService.deleteAllCartBook(cartId);
            response.put("success", true);
            response.put("message", "장바구니가 비워졌습니다.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "장바구니 비우기 실패");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/carts")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteCartBook(@RequestParam("cartId") List<Long> cartId,
                                                              @RequestParam("cartBookId") List<Long> cartBookId) {
        Map<String, Object> response = new HashMap<>();
        try {
            for (int i = 0; i < cartId.size(); i++) {
                DeleteCartBookRequest deleteCartBookRequest = DeleteCartBookRequest.builder()
                        .cartId(cartId.get(i))
                        .cartBookId(cartBookId.get(i))
                        .build();
                cartService.deleteCartBook(deleteCartBookRequest);
            }
            response.put("success", true);
            response.put("message", "장바구니에서 상품이 삭제되었습니다.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "장바구니 상품 삭제 실패");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
