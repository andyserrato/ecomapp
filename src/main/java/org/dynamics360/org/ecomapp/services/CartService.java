package org.dynamics360.org.ecomapp.services;

import org.dynamics360.org.ecomapp.dtos.CartDto;
import org.dynamics360.org.ecomapp.exceptions.CartNotFoundException;
import org.dynamics360.org.ecomapp.exceptions.ProductNotFoundException;

public interface CartService {
    CartDto createCart();

    CartDto getCartById(Long cartId);

    CartDto addToCart(Long productId, String cartId, Long quantity) throws CartNotFoundException, ProductNotFoundException;
}
