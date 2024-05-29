package org.dynamics360.org.ecomapp.dtos;

public record CartEntryDto(
        ProductDto product,
        Long quantity,
        Double basePrice,
        Double totalPrice
) {
}
