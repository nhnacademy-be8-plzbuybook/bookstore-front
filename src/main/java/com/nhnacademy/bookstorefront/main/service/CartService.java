package com.nhnacademy.bookstorefront.main.service;

import com.nhnacademy.bookstorefront.main.dto.cart.request.CreateCartBookRequest;
import com.nhnacademy.bookstorefront.main.dto.cart.request.DeleteCartBookRequest;
import com.nhnacademy.bookstorefront.main.dto.cart.request.UpdateCartBookRequest;
import com.nhnacademy.bookstorefront.main.dto.cart.response.ReadCartBookResponse;

import java.util.List;

public interface CartService {
    Long createCart(CreateCartBookRequest createCartBookRequest);

    List<ReadCartBookResponse> getCartBooks();

    Long updateCartBook(UpdateCartBookRequest updateCartBookRequest);

    String deleteAllCartBook(Long cartId);

    String deleteCartBook(DeleteCartBookRequest deleteCartBookRequest);
}
