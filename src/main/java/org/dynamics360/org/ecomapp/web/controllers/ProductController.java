package org.dynamics360.org.ecomapp.web.controllers;

import org.dynamics360.org.ecomapp.dtos.ProductDto;
import org.dynamics360.org.ecomapp.persistence.entities.Product;
import org.dynamics360.org.ecomapp.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * ProductController is a REST controller for managing products in the e-commerce system.
 * It provides endpoints for creating, reading, updating, and deleting products.
 *
 * @author andyserrato
 */
 @RestController
 @RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Retrieve a product by its ID.
     *
     * @param productId the ID of the product to retrieve
     * @return a {@code ResponseEntity} containing the requested product and HTTP status 200 (OK) if found,
     *         or HTTP status 404 (Not Found) if the product does not exist
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable String productId) {
        ProductDto productDto = productService.findProductById(Long.valueOf(productId));

        if (productDto != null) {
            return ResponseEntity.ok(productDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Create a new product.
     *
     * @param product the product to create
     * @return a {@code ResponseEntity} containing the created product and HTTP status 201 (Created)
     */
    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody ProductDto product, UriComponentsBuilder ucb) {
        Product createdProduct = productService.saveProduct(product);
        URI locationOfNewCashCard = ucb
                .path("products/{id}")
                .buildAndExpand(createdProduct.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }

    /**
     * Retrieve a list of all products.
     *
     * @return a {@code ResponseEntity} containing a list of all products and HTTP status 200 (OK)
     */
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(Pageable pageable) {
        Page<ProductDto> productDtoPage = productService.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.DESC, "price"))
                )
        );

        return ResponseEntity.ok(productDtoPage.getContent());
    }

    /**
     * Update an existing product.
     *
     * @param productId the ID of the product to update
     * @param productDto the product details to update
     * @return a {@code ResponseEntity} containing the requested product and HTTP status 204 (No Content) if found,
     *         or HTTP status 404 (Not Found) if the product does not exist
     */
    @PutMapping("/{productId}")
    public ResponseEntity<Void> putProduct(@PathVariable String productId, @RequestBody ProductDto productDto) {
        if (productService.existsProductById(Long.valueOf(productId))) {
            productService.saveProduct(productDto);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a product by its ID.
     *
     * @param productId the ID of the product to delete
     * @return a {@code ResponseEntity} with HTTP status 204 (No Content) if the deletion is successful,
     *         or HTTP status 404 (Not Found) if the product does not exist
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productId) {
        if (productService.existsProductById(Long.valueOf(productId))) {
            productService.deleteProduct(Long.valueOf(productId));
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
