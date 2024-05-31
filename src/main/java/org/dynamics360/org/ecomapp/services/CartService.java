package org.dynamics360.org.ecomapp.services;

import org.dynamics360.org.ecomapp.dtos.CartDto;
import org.dynamics360.org.ecomapp.dtos.CartEntryDto;
import org.dynamics360.org.ecomapp.exceptions.CartEntryNotFoundException;
import org.dynamics360.org.ecomapp.exceptions.CartNotFoundException;
import org.dynamics360.org.ecomapp.exceptions.ProductNotFoundException;

public interface CartService {
    CartDto createCart();

    CartDto getCartById(Long cartId);

    CartDto addToCart(Long productId, Long cartId, Long quantity) throws CartNotFoundException, ProductNotFoundException;

    CartDto emptyCart(Long cartId) throws CartNotFoundException;

    void removeProductFromCart(Long productId, Long cartId) throws CartNotFoundException;

    void removeCart(Long cartId) throws CartNotFoundException;

    void removeCartEntry(Long entryId, Long cartId) throws CartEntryNotFoundException, CartNotFoundException;

    void updateCartEntry(CartEntryDto cartEntryDto, Long cart) throws CartNotFoundException, CartEntryNotFoundException;
}
