package com.crm.wm.repository;

import com.crm.wm.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    default void updateProduct(Product product) {
        save(product);
    }
}
