package org.dynamics360.org.ecomapp.persistence.repositories;

import org.dynamics360.org.ecomapp.persistence.entities.Cart;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, Long> {
}
