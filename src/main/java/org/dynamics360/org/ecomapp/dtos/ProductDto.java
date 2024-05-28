package org.dynamics360.org.ecomapp.dtos;

public record ProductDto(
        Long id,
        String name,
        String description,
        Double price
) {
}
