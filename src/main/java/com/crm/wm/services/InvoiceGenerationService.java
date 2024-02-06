package com.crm.wm.services;

import com.crm.wm.entities.Customer;
import com.crm.wm.entities.Invoice;
import com.crm.wm.entities.Product;
import com.crm.wm.repository.CustomerRepository;
import com.crm.wm.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class InvoiceGenerationService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CustomerRepository customerRepository;

    // Other dependencies...

    @Transactional
    public void generateInvoices() {
        // Logic to fetch customers and generate invoices based on associated products
        List<Customer> customers = customerRepository.findAll();
        for (Customer customer : customers) {
            Invoice invoice = new Invoice();
            invoice.setCustomer(customer);

            // Calculate the total amount based on the customer's associated products
            double totalAmount = calculateTotalAmount(customer.getProducts());

            // Add additional products (expenses) for the month
            List<Product> additionalProducts = getAdditionalProductsForCustomer(customer);
            totalAmount += calculateTotalAmount(additionalProducts);

            invoice.setTotalAmount(totalAmount);

            // Save the invoice
            invoiceRepository.save(invoice);
        }
    }

    private double calculateTotalAmount(List<Product> products) {
        // Calculate the total amount based on the products
        // You can implement your specific logic here
        // For example, summing the prices of all products
        return products.stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    private List<Product> getAdditionalProductsForCustomer(Customer customer) {
        // Logic to fetch additional products (expenses) for the customer for the month
        // You may retrieve these from a repository or another data source
        // For demonstration purposes, return an empty list
        return Collections.emptyList();
    }
}
