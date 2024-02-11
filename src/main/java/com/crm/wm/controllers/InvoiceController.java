package com.crm.wm.controllers;

import com.crm.wm.entities.Customer;
import com.crm.wm.entities.Invoice;
import com.crm.wm.entities.Product;
import com.crm.wm.repository.CustomerRepository;
import com.crm.wm.repository.ProductRepository;
import com.crm.wm.services.InvoiceGenerationException;
import com.crm.wm.services.InvoiceService;
import com.crm.wm.services.ProductWithQuantity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<String> generateInvoiceManually(
            @RequestParam Long customerId,
            @RequestParam Long employeeId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") Date month,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date invoiceDate,
            @RequestParam List<Long> productIds,
            @RequestParam(required = false) List<Integer> quantities) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        try {
            // Fetch products based on provided productIds
            List<Product> products = productRepository.findAllById(productIds);

            // Prepare products with quantities
            List<ProductWithQuantity> productsWithQuantity;
            if (quantities != null && quantities.size() == productIds.size()) {
                productsWithQuantity = InvoiceControllerUtil.mapProductsWithQuantities(products, quantities);
            } else {
                productsWithQuantity = InvoiceControllerUtil.mapProductsWithQuantities(products);
            }

            // Generate the invoice manually and add the selected products
            Invoice generatedInvoice = invoiceService.generateInvoiceManually(customer, invoiceDate, employeeId, month, productsWithQuantity);
            return ResponseEntity.ok("Invoice generated successfully. Invoice ID: " + generatedInvoice.getInvoiceID());
        } catch (InvoiceGenerationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
