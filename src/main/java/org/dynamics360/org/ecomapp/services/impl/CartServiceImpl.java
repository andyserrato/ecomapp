package org.dynamics360.org.ecomapp.services.impl;

import org.dynamics360.org.ecomapp.dtos.CartDto;
import org.dynamics360.org.ecomapp.exceptions.CartNotFoundException;
import org.dynamics360.org.ecomapp.exceptions.ProductNotFoundException;
import org.dynamics360.org.ecomapp.mappers.CartMapper;
import org.dynamics360.org.ecomapp.persistence.entities.Cart;
import org.dynamics360.org.ecomapp.persistence.entities.CartEntry;
import org.dynamics360.org.ecomapp.persistence.entities.Product;
import org.dynamics360.org.ecomapp.persistence.repositories.CartEntryRepository;
import org.dynamics360.org.ecomapp.persistence.repositories.CartRepository;
import org.dynamics360.org.ecomapp.persistence.repositories.ProductRepository;
import org.dynamics360.org.ecomapp.services.CartService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    public static final String CART_NOT_FOUND = "Cart not found";
    public static final String PRODUCT_NOT_FOUND = "Product not found";
    private final CartRepository cartRepository;
    private final CartEntryRepository cartEntryRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository, CartEntryRepository cartEntryRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartEntryRepository = cartEntryRepository;
    }

    @Override
    public CartDto createCart() {
        return CartMapper.toDto(cartRepository.save(new Cart()));
    }

    @Override
    public CartDto getCartById(Long cartId) {
        Optional<Cart> cart = cartRepository.findById(cartId);
        return cart.map(CartMapper::toDto).orElse(null);
    }

    @Override
    public CartDto addToCart(Long productId, String cartId, Long quantity) throws CartNotFoundException, ProductNotFoundException {
        Cart cart = cartRepository.findById(Long.valueOf(cartId))
                .orElseThrow(() -> new CartNotFoundException(CART_NOT_FOUND));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));

        // TODO Search if product already exists in cart
        // TODO If product already exists change the entry

        // else create new order entry
        CartEntry cartEntry = createCartEntry(quantity, product, cart);

        cart = addCartEntryToCart(cart, cartEntry);

        // calculate the cart
        cart = calculateCart(cart);

        return Optional.of(cart).map(CartMapper::toDto).orElse(null);
    }

    @Override
    public CartDto emptyCart(Long cartId) throws CartNotFoundException {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException(CART_NOT_FOUND));

        cart.setTotalPrice(0.0d);
        cart.setCartEntries(new ArrayList<>());
        return Optional.of(cartRepository.save(cart)).map(CartMapper::toDto).orElse(null);
    }

    @Override
    public void removeProductFromCart(Long productId, Long cartId) throws CartNotFoundException {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException(CART_NOT_FOUND));

        for (var entry : cart.getCartEntries()) {
            if(entry.getProduct().getId().equals(productId)) {
                cart.getCartEntries().remove(entry);
                calculateCart(cart);
                cartEntryRepository.delete(entry);
                break;
            }
        }
    }

    private Cart calculateCart(Cart cart) {
        var totalCartPrice = 0.0d;
        for (CartEntry entry : cart.getCartEntries()) {
             totalCartPrice += entry.getTotalPrice();
        }
        cart.setTotalPrice(totalCartPrice);

        cart = cartRepository.save(cart);
        return cart;
    }

    private Cart addCartEntryToCart(Cart cart, CartEntry cartEntry) {
        cart.getCartEntries().add(cartEntry);
        cart = cartRepository.save(cart);
        return cart;
    }

    private CartEntry createCartEntry(Long quantity, Product product, Cart cart) {
        CartEntry cartEntry = new CartEntry();
        cartEntry.setQuantity(quantity);
        cartEntry.setProduct(product);
        cartEntry.setCart(cart);
        cartEntry.setBasePrice(product.getPrice());
        cartEntry.setTotalPrice(product.getPrice() * quantity);
        cartEntryRepository.save(cartEntry);
        return cartEntry;
    }
}
