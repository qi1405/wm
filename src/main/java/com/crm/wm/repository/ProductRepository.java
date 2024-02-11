package com.crm.wm.repository;

import com.crm.wm.entities.Customer;
import com.crm.wm.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // You can add custom queries or methods if needed
    List<Product> findByCustomer(Customer customer);
}
