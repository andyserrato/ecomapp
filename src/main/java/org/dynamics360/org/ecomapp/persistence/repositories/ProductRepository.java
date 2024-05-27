package org.dynamics360.org.ecomapp.persistence.repositories;

import org.dynamics360.org.ecomapp.persistence.entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends CrudRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {
}
