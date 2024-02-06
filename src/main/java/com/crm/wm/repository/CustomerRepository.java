package com.crm.wm.repository;

import com.crm.wm.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Add custom queries if needed
}
