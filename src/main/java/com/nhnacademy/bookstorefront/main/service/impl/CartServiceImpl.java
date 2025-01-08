package com.nhnacademy.bookstorefront.main.service.impl;

import com.nhnacademy.bookstorefront.main.client.CartClient;
import com.nhnacademy.bookstorefront.main.dto.cart.request.CreateCartBookRequest;
import com.nhnacademy.bookstorefront.main.dto.cart.request.DeleteCartBookRequest;
import com.nhnacademy.bookstorefront.main.dto.cart.request.UpdateCartBookRequest;
import com.nhnacademy.bookstorefront.main.dto.cart.response.ReadCartBookResponse;
import com.nhnacademy.bookstorefront.main.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    private final CartClient cartClient;

    public CartServiceImpl(CartClient cartClient) {
        this.cartClient = cartClient;
    }

    @Override
    public Long createCart(CreateCartBookRequest createCartBookRequest) {
        return cartClient.createCartBook(createCartBookRequest).getBody();
    }

    @Override
    public List<ReadCartBookResponse> getCartBook(Long cartBookId) {
        return cartClient.getCartBook(cartBookId).getBody();
    }

    @Override
    public List<ReadCartBookResponse> getCartBooks() {
        return cartClient.getCartBooks().getBody();
    }

    @Override
    public Long updateCartBook(UpdateCartBookRequest updateCartBookRequest) {
        return cartClient.updateCartBook(updateCartBookRequest).getBody();
    }

    @Override
    public String deleteCart(Long cartId) {
        return cartClient.deleteCartBook(cartId).getBody();
    }

    @Override
    public String deleteCartBook(DeleteCartBookRequest deleteCartBookRequest) {
        return cartClient.deleteAllCartBook(deleteCartBookRequest).getBody();
    }

}
