
package com.nhnacademy.bookstorefront.main.controller.cart;


import com.nhnacademy.bookstorefront.main.dto.cart.request.CreateCartBookRequest;
import com.nhnacademy.bookstorefront.main.dto.cart.response.ReadCartBookResponse;
import com.nhnacademy.bookstorefront.main.service.GuestCartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
public class GuestCartController {

    private final GuestCartService guestCartService;

    public GuestCartController(GuestCartService guestCartService) {
        this.guestCartService = guestCartService;
    }

    @GetMapping("/guests/carts")
    public String getGuestCart(HttpSession session,
                               HttpServletRequest request,
                               Model model) {
        List<ReadCartBookResponse> readCartBookResponses =  guestCartService.getGuestCarts(session);
        model.addAttribute("cartBooks", readCartBookResponses);

        return "cart/guestCart";
    }

    @PostMapping("/guests/carts")
    @ResponseBody
    public ResponseEntity<String> CreateCart(@RequestBody CreateCartBookRequest createCartBookRequest,
                                     HttpSession session,
                                     Model model) {
        try{
            Long cartId = guestCartService.addGuestCart(createCartBookRequest, session);
            model.addAttribute("cartId", cartId);
            return ResponseEntity.status(201).body("장바구니에 상품이 추가되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("장바구니에 상품 추가 실패");
        }

    }

    @PutMapping("/guests/carts")
    public ResponseEntity<String> updateGuestCart(@RequestParam Long cartId,
                                  @RequestParam int quantity,
                                  HttpServletRequest request,
                                  HttpSession session,
                                  Model model) {
        try{
            Long updatedCartId = guestCartService.updateGuestCart(cartId, quantity, session);
            model.addAttribute("cartId", updatedCartId);
            return ResponseEntity.status(204).body("장바구니 수량이 수정되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("장바구니 수량 수정 실패");
        }
    }

    @DeleteMapping("/guests/carts/{cartId}")
    public ResponseEntity<String> deleteGuestCartItem(@PathVariable("cartId") Long cartId,
                                      HttpSession session,
                                      HttpServletRequest request,
                                      Model model) {
        try{
            guestCartService.deleteGuestCartItem(cartId, session);
            return ResponseEntity.status(204).body("장바구니 상품이 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("장바구니 상품 삭제 실패");
        }
    }

    @DeleteMapping("/guests/carts")
    public ResponseEntity<String> deleteAllGuestCartItems(HttpSession session,
                                          HttpServletRequest request,
                                          Model model) {
        try{
            guestCartService.deleteAllGuestCartItems(session);
            return ResponseEntity.status(204).body("장바구니가 비워졌습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("장바구니 비우기 실패");
        }
    }

}
