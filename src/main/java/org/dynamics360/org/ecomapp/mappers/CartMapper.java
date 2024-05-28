package org.dynamics360.org.ecomapp.mappers;

import org.dynamics360.org.ecomapp.dtos.CartDto;
import org.dynamics360.org.ecomapp.persistence.entities.Cart;

public class CartMapper {

    private CartMapper() {
    }


    public static CartDto toDto(Cart cart) {
        return new CartDto(
                cart.getId(),
                cart.getCartEntries().stream().map(CartEntryMapper::toDto).toList(),
                cart.getTotalPrice()
        );
    }
}
