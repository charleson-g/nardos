package com.nardo.platform.persistence;

import com.nardo.platform.business.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Persistence Layer interface for Inventory data.
 * This class provides the technical service for saving/retrieving Product entities
 * to the MySQL Cloud Database, supporting the "Sure Money" logic.
 */

@Repository

public interface InventoryRepository extends JpaRepository<Product, String> {

    // Spring Data JPA automatically provides methods like:
    // .save(Product p) -> Maps to SQL INSERT or UPDATE
    // .findById(String id) -> Maps to SQL SELECT
    // .delete(Product p) -> Maps to SQL DELETE
}
