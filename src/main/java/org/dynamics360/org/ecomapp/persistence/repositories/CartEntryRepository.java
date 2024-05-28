package org.dynamics360.org.ecomapp.persistence.repositories;

import org.dynamics360.org.ecomapp.persistence.entities.CartEntry;
import org.springframework.data.repository.CrudRepository;

public interface CartEntryRepository extends CrudRepository<CartEntry, Long> {
}
