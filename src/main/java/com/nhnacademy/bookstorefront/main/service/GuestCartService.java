package com.nhnacademy.bookstorefront.main.service;

import com.nhnacademy.bookstorefront.main.dto.cart.request.CreateCartBookRequest;
import com.nhnacademy.bookstorefront.main.dto.cart.response.ReadCartBookResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface GuestCartService {

    Long addGuestCart(CreateCartBookRequest createCartBookRequest, HttpSession session);

    List<ReadCartBookResponse> getGuestCarts(HttpSession session);

    Long updateGuestCart(Long cartId, int quantity, HttpSession session);

    Long deleteGuestCartItem(Long cartId, HttpSession session);

    void deleteAllGuestCartItems(HttpSession session);
}
