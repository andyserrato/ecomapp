package org.dynamics360.org.ecomapp.mappers;

import org.dynamics360.org.ecomapp.dtos.CartEntryDto;
import org.dynamics360.org.ecomapp.persistence.entities.CartEntry;

public class CartEntryMapper {

    private CartEntryMapper() {
    }

    public static CartEntryDto toDto(CartEntry cartEntry) {
        return new CartEntryDto(
                ProductMapper.toDto(cartEntry.getProduct()),
                cartEntry.getQuantity(),
                cartEntry.getBasePrice(),
                cartEntry.getTotalPrice()
        );
    }
}
