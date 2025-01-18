
package com.nhnacademy.bookstorefront.main.controller.cart;


import com.nhnacademy.bookstorefront.main.client.AuthenticationClient;
import com.nhnacademy.bookstorefront.main.dto.cart.request.CreateCartBookRequest;
import com.nhnacademy.bookstorefront.main.dto.cart.response.ReadCartBookResponse;
import com.nhnacademy.bookstorefront.main.service.AuthenticationService;
import com.nhnacademy.bookstorefront.main.service.GuestCartService;
import jakarta.servlet.http.Cookie;
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
    private final AuthenticationService authenticationService;
    private final AuthenticationClient authenticationClient;

    public GuestCartController(GuestCartService guestCartService,
                               AuthenticationService authenticationService,
                               AuthenticationClient authenticationClient) {
        this.guestCartService = guestCartService;
        this.authenticationService = authenticationService;
        this.authenticationClient = authenticationClient;
    }

    @GetMapping("/guests/carts")
    public String getGuestCart(HttpSession session,
                               HttpServletRequest request,
                               Model model) {
        List<ReadCartBookResponse> readCartBookResponses =  guestCartService.getGuestCarts(session);
        roleAndIsLoggedIn(request, model);
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
    public String updateGuestCart(@RequestParam Long cartId,
                                  @RequestParam int quantity,
                                  HttpServletRequest request,
                                  HttpSession session,
                                  Model model) {
        Long updatedCartId = guestCartService.updateGuestCart(cartId, quantity, session);
        roleAndIsLoggedIn(request, model);
        model.addAttribute("cartId", updatedCartId);

        return "cart/guestCart";
    }

    @DeleteMapping("/guests/carts/{cartId}")
    public String deleteGuestCartItem(@PathVariable("cartId") Long cartId,
                                      HttpSession session,
                                      HttpServletRequest request,
                                      Model model) {
        guestCartService.deleteGuestCartItem(cartId, session);
        roleAndIsLoggedIn(request, model);
        return "cart/guestCart";
    }

    @DeleteMapping("/guests/carts")
    public String deleteAllGuestCartItems(HttpSession session,
                                          HttpServletRequest request,
                                          Model model) {
        guestCartService.deleteAllGuestCartItems(session);
        roleAndIsLoggedIn(request, model);
        return "cart/guestCart";
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

    private void roleAndIsLoggedIn(HttpServletRequest request, Model model) {
        boolean isLoggedIn = authenticationService.isLoggedIn(request);

        String role = null;

        if(isLoggedIn) {
            String token = getTokenFromCookies(request);
            if(token != null) {
                role = authenticationClient.getRoleFromToken("Bearer " + token).getBody();
            }
        }
        model.addAttribute("role", role);
        model.addAttribute("isLoggedIn", isLoggedIn);
    }



}
