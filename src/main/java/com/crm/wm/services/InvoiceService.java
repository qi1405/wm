package com.crm.wm.services;

import com.crm.wm.entities.*;
import com.crm.wm.repository.EmployeeRepository;
import com.crm.wm.repository.InvoiceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProductService productService;

    // Other dependencies...

    @Transactional
    public Invoice generateInvoiceManually(Customer customer, Date invoiceDate, Long employeeId, Date month, List<ProductWithQuantity> productsWithQuantity) {
        // Set invoiceDate to current date if not provided
        if (invoiceDate == null) {
            invoiceDate = Calendar.getInstance().getTime();
        }

        // Check if an invoice for the given month already exists
        if (invoiceRepository.existsByCustomerAndMonth(customer, month)) {
            throw new InvoiceGenerationException("Invoice already generated for the specified month.");
        }

        // Fetch employee details
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        // Fetch prices of products associated with the customer
        Map<Long, Double> productPrices = productService.getPricesForProductsAssociatedWithCustomer(customer);

        // Logic to generate the invoice manually
        Invoice invoice = new Invoice();
        invoice.setCustomer(customer);
        invoice.setEmployee(employee); // Set the employee generating the invoice
        invoice.setMonth(month); // Set the month of the invoice
        invoice.setInvoiceDate(new Date()); // Set the current date as the invoice date, you can modify this as needed

        // Create InvoiceItem objects for each product and add them to the invoice
        double totalAmount = 0.0;
        for (ProductWithQuantity productWithQuantity : productsWithQuantity) {
            Product product = productWithQuantity.getProduct();
            int quantity = productWithQuantity.getQuantity();

            for (int i = 0; i < quantity; i++) {
                InvoiceItem invoiceItem = new InvoiceItem();
                invoiceItem.setProduct(product);
                invoiceItem.setQuantity(1); // Default quantity to 1

                // Set product price based on association with the customer
                if (productPrices.containsKey(product.getProductID())) {
                    double price = productPrices.get(product.getProductID());
                    invoiceItem.setPrice(price);
                    totalAmount += price;
                } else {
                    // Handle scenario where price is not available for the product
                    // You can throw an exception or set a default price
                }

                // Set other invoice item details as needed
                invoice.getInvoiceItems().add(invoiceItem);
            }
        }

        // Set total amount for the invoice
        invoice.setTotalAmount(totalAmount);

        // Save the manually generated invoice
        return invoiceRepository.save(invoice);
    }

    public boolean isInvoiceGeneratedForMonth(Customer customer, Date invoiceMonth) {
        return invoiceRepository.existsByCustomerAndMonth(customer, invoiceMonth);
    }
}

