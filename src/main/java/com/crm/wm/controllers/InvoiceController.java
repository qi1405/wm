package com.crm.wm.controllers;

import com.crm.wm.entities.Customer;
import com.crm.wm.entities.Invoice;
import com.crm.wm.repository.CustomerRepository;
import com.crm.wm.services.InvoiceGenerationException;
import com.crm.wm.services.InvoiceService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/generate")
    public ResponseEntity<String> generateInvoiceManually(
            @RequestParam Long customerId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") Date invoiceDate) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        try {
            Invoice generatedInvoice = invoiceService.generateInvoiceManually(customer, invoiceDate);
            return ResponseEntity.ok("Invoice generated successfully. Invoice ID: " + generatedInvoice.getInvoiceID());
        } catch (InvoiceGenerationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
