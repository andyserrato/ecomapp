package org.dynamics360.org.ecomapp.web.controllers;

import org.dynamics360.org.ecomapp.dtos.CartDto;
import org.dynamics360.org.ecomapp.exceptions.CartNotFoundException;
import org.dynamics360.org.ecomapp.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CartController is a REST controller for managing carts in the e-commerce system.
 * It provides endpoints for creating, reading, updating, and deleting carts.
 *
 * @author andyserrato
 */
@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @PostMapping
    public ResponseEntity<CartDto> createCart() {
        return ResponseEntity.ok(cartService.createCart());
    }

    /**
     *
     * @param cartId the ID of the cart to retrieve
     * @return
     */
    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable String cartId) {
        CartDto cartDto = cartService.getCartById(Long.valueOf(cartId));

        if (cartDto != null) {
            return ResponseEntity.ok(cartDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Remove Cart
     */
    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> removeCart(@PathVariable String cartId) throws CartNotFoundException {
        cartService.removeCart(Long.valueOf(cartId));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/empty")
    public ResponseEntity<CartDto> emptyCart(@PathVariable String cartId) throws CartNotFoundException {
        return ResponseEntity.ok(cartService.emptyCart(Long.valueOf(cartId)));
    }

}
