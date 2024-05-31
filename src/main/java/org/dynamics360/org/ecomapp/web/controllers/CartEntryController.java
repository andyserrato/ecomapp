package org.dynamics360.org.ecomapp.web.controllers;

import org.dynamics360.org.ecomapp.dtos.CartDto;
import org.dynamics360.org.ecomapp.dtos.CartEntryDto;
import org.dynamics360.org.ecomapp.exceptions.CartEntryNotFoundException;
import org.dynamics360.org.ecomapp.exceptions.CartNotFoundException;
import org.dynamics360.org.ecomapp.exceptions.ProductNotFoundException;
import org.dynamics360.org.ecomapp.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts/{cartId}/entries")
public class CartEntryController {

    private final CartService cartService;

    public CartEntryController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * Add to cart products.
     * @param cartEntryDto the cart entry to create
     * @param cartId the id of the cart to retrieve
     * @return a {@code ResponseEntity} containing the cart with the added product and HTTP status 200 (OK)
     */
    @PostMapping
    public ResponseEntity<CartDto> addToCart(@RequestBody CartEntryDto cartEntryDto, @PathVariable String cartId) throws ProductNotFoundException, CartNotFoundException {
        return ResponseEntity.ok(cartService.addToCart(cartEntryDto.product().id(), Long.valueOf(cartId), cartEntryDto.quantity()));
    }


    @PutMapping
    public ResponseEntity<Void> updateCartEntry(@RequestBody CartEntryDto cartEntryDto, @PathVariable String cartId) throws CartNotFoundException, CartEntryNotFoundException {
        cartService.updateCartEntry(cartEntryDto, Long.valueOf(cartId));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{entryId}")
    public ResponseEntity<Void> removeCartEntry(@PathVariable String entryId, @PathVariable String cartId) throws CartNotFoundException, CartEntryNotFoundException {
        cartService.removeCartEntry(Long.valueOf(entryId), Long.valueOf(cartId));
        return ResponseEntity.noContent().build();
    }
}
