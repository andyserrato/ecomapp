package org.dynamics360.org.ecomapp.mappers;

import org.dynamics360.org.ecomapp.dtos.ProductDto;
import org.dynamics360.org.ecomapp.persistence.entities.Product;

/**
 * The ProductMapper class handles the mapping between product entity and the product DTO
 * @author andyserrato
 */
public class ProductMapper {

    private ProductMapper() {
    }

    /**
     * Converts a product entity into a product DTO.
     * @param product the product entity.
     * @return productDTO the product DTO.
     */
    public static ProductDto toDto(Product product) {
        if (product == null) {
            return null;
        }

        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }

    /**
     * Converts a ProductDTO to a Product entity.
     *
     * @param productDto the productDTO to convert
     * @return the converted Product entity
     */
    public static Product toEntity(ProductDto productDto) {
        if (productDto == null) {
            return null;
        }

        return new Product(
                productDto.id(),
                productDto.name(),
                productDto.description(),
                productDto.price()
        );
    }
}
