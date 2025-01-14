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
        model.addAttribute("cartBooks", readCartBookResponses);
        log.info("cartBooks: {}", readCartBookResponses);


        return "cart/guestCart";
    }


    @PostMapping("/guests/carts")
    public String CreateCart(@RequestBody CreateCartBookRequest createCartBookRequest,
                             HttpSession session,
                             Model model) {
        Long cartId = guestCartService.addGuestCart(createCartBookRequest, session);
        model.addAttribute("cartId", cartId);

        return "redirect:/guests/carts";
    }

    @PutMapping("/guests/carts")
    public String updateGuestCart(Long cartId, int quantity,
                                  HttpSession session,
                                  Model model) {
        Long updatedCartId = guestCartService.updateGuestCart(cartId, quantity, session);
        model.addAttribute("cartId", updatedCartId);
        return "redirect:/guests/carts";
    }

    @DeleteMapping("/guests/carts/{cartId}")
    public String deleteGuestCartItem(@PathVariable Long cartId,
                                      HttpSession session,
                                      Model model) {

        Long deletedCartId = guestCartService.deleteGuestCartItem(cartId, session);
        model.addAttribute("cartId", deletedCartId);
        return "redirect:/guests/carts";
    }

    @DeleteMapping("/guests/carts")
    public String deleteAllGuestCartItems(HttpSession session) {
        guestCartService.deleteAllGuestCartItems(session);
        return "redirect:/guests/carts";
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
