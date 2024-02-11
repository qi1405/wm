package com.crm.wm.repository;

import com.crm.wm.entities.Customer;
import com.crm.wm.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    // You can add custom queries if needed

    boolean existsByCustomerAndInvoiceDate(Customer customer, Date invoiceDate);
    boolean existsByCustomerAndMonth(Customer customer, Date month);
}
