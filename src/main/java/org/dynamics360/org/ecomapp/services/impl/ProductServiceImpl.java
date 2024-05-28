package org.dynamics360.org.ecomapp.services.impl;

import org.dynamics360.org.ecomapp.dtos.ProductDto;
import org.dynamics360.org.ecomapp.mappers.ProductMapper;
import org.dynamics360.org.ecomapp.persistence.entities.Product;
import org.dynamics360.org.ecomapp.persistence.repositories.ProductRepository;
import org.dynamics360.org.ecomapp.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDto findProductById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {
            return ProductMapper.toDto(product.get());
        } else {
            return null;
        }
    }

    @Override
    public Product saveProduct(ProductDto productDTO) {
        Product product = ProductMapper.toEntity(productDTO);
        return productRepository.save(product);
    }

    @Override
    public Page<ProductDto> findAll(PageRequest page) {
        return productRepository
                .findAll(page)
                .map(ProductMapper::toDto);
    }

    @Override
    public boolean existsProductById(Long id) {
        return productRepository.existsById(id);

    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
