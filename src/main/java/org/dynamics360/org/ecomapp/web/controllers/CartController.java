package org.dynamics360.org.ecomapp.web.controllers;

import org.dynamics360.org.ecomapp.dtos.CartDto;
import org.dynamics360.org.ecomapp.dtos.CartEntryDto;
import org.dynamics360.org.ecomapp.exceptions.CartNotFoundException;
import org.dynamics360.org.ecomapp.exceptions.ProductNotFoundException;
import org.dynamics360.org.ecomapp.services.CartService;
import org.dynamics360.org.ecomapp.services.ProductService;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<CartDto> createCart() {
        return ResponseEntity.ok(cartService.createCart());
    }

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
     * ● Add a product to the cart: It should be possible to add existing products to the cart.
     * @param cartEntryDto
     * @param cartId
     * @return
     */
    @PostMapping("/entries/{cartId}")
    public ResponseEntity<CartDto> addToCart(@RequestBody CartEntryDto cartEntryDto, @PathVariable String cartId) throws ProductNotFoundException, CartNotFoundException {
        return ResponseEntity.ok(cartService.addToCart(cartEntryDto.product().id(), cartId, cartEntryDto.quantity()));
    }

    /**
     * Remove a product from the cart
     */
    @DeleteMapping("/entries/{cartId}")
    public ResponseEntity<Void> removeAProductFromCart(@RequestBody CartEntryDto cartEntryDto, @PathVariable String cartId) throws ProductNotFoundException, CartNotFoundException {
        if (cartService.addToCart(cartEntryDto.product().id(), cartId, cartEntryDto.quantity()) != null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Empty cart
     */

}
