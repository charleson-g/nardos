package com.nardo.platform.persistence;

import com.nardo.platform.business.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Product, String> {
    List<Product> findByCurrentQuantityGreaterThan(int qty);
    List<Product> findByIsAvailableTrue();
    List<Product> findByCurrentQuantityGreaterThanAndIsAvailableTrue(int qty);
}
