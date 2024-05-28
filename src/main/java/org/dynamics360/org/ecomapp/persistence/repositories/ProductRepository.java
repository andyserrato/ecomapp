package org.dynamics360.org.ecomapp.persistence.repositories;

import org.dynamics360.org.ecomapp.persistence.entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


/**
 * ProductRepository is an interface for performing CRUD operations on the Product entity.
 * It extends CrudRepository and PagingAndSortingRepository to provide standard methods for interacting with the database.
 *
 * @author andyserrato
 */
 public interface ProductRepository extends CrudRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {
}
