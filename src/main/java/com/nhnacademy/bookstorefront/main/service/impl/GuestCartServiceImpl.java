package com.nhnacademy.bookstorefront.main.service.impl;

import com.nhnacademy.bookstorefront.main.client.GuestCartClient;
import com.nhnacademy.bookstorefront.main.dto.cart.request.CreateCartBookRequest;
import com.nhnacademy.bookstorefront.main.dto.cart.response.ReadCartBookResponse;
import com.nhnacademy.bookstorefront.main.service.GuestCartService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class GuestCartServiceImpl implements GuestCartService {

    private final GuestCartClient guestCartClient;

    public GuestCartServiceImpl(GuestCartClient guestCartClient) {
        this.guestCartClient = guestCartClient;
    }


    @Override
    public Long addGuestCart(CreateCartBookRequest createCartBookRequest, HttpSession session) {
        String sessionId = session.getId();
        Map<String, Long> response = guestCartClient.createGuestCartBook(createCartBookRequest, sessionId);
        log.warn("Adding to guest cart: {}", createCartBookRequest);
        log.warn("Session ID: {}", sessionId);
        log.warn("Response: {}", response);
        return response.get("cartId");
    }

    @Override
    public List<ReadCartBookResponse> getGuestCarts(HttpSession session) {
        String sessionId = session.getId();
        log.warn("Getting guest cart for session ID: {}", sessionId);
        log.warn("Response: {}", guestCartClient.getGuestCartBooks(sessionId));
        return guestCartClient.getGuestCartBooks(sessionId);
    }

    @Override
    public Long updateGuestCart(Long cartId, int quantity, HttpSession session) {
        String sessionId = session.getId();
        log.warn("Updating guest cart item: cartId={}, quantity={}", cartId, quantity);
        log.warn("Session ID: {}", sessionId);
        Map<String, Long> response = guestCartClient.updateGuestCartBook(cartId, quantity, sessionId);
        return response.get("cartId");
    }

    @Override
    public void deleteGuestCartItem(Long cartId, HttpSession session) {
        String sessionId = session.getId();
        log.warn("Deleting guest cart item: cartId={}", cartId);
        log.warn("Session ID: {}", sessionId);
        guestCartClient.deleteGuestCartBook(cartId, sessionId);
    }

    @Override
    public void deleteAllGuestCartItems(HttpSession session) {
        String sessionId = session.getId();
        log.warn("Session ID: {}", sessionId);
        guestCartClient.deleteAllGuestCart(sessionId);
    }
}
