package org.dynamics360.org.ecomapp.dtos;

import java.util.List;

public record CartDto (
        Long id,
        List<CartEntryDto> cartEntries,
        Double totalPrice
) {
}
