package org.dynamics360.org.ecomapp.services;

import org.dynamics360.org.ecomapp.dtos.ProductDto;
import org.dynamics360.org.ecomapp.persistence.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * ProductService is an interface that provides business logic for managing products in the e-commerce system.
 * It defines methods for creating, retrieving, updating, and deleting products.
 * @author andyserrato
 */
public interface ProductService {

    /**
     * Retrieves a product by its ID.
     *
     * @param productId the ID of the product to retrieve
     * @return the product with the given ID, or {@code null} if no such product exists
     */
    ProductDto findProductById(Long productId);

    /**
     * Creates a new product.
     *
     * @param productDTO the product data transfer object containing the details of the product to create
     * @return the created product
     */
    Product saveProduct(ProductDto productDTO);

    /**
     * Retrieves a list of all products.
     *
     * @return a list of all products paged and sorted.
     */
    Page<ProductDto> findAll(PageRequest page);

    /**
     * Checks if a product exists by its ID.
     *
     * @param productId the ID of the product to check
     * @return {@code true} if a product with the given ID exists, {@code false} otherwise
     */
    boolean existsProductById(Long productId);

    /**
     * Deletes a product by its ID.
     *
     * @param productId the ID of the product to delete
     */
    void deleteProduct(Long productId);
}
