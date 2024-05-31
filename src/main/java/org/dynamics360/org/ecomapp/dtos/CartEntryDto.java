package org.dynamics360.org.ecomapp.dtos;

public record CartEntryDto(
        Long id,
        ProductDto product,
        Long quantity,
        Double basePrice,
        Double totalPrice
) {
}
