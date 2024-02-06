package com.crm.wm.services;

import com.crm.wm.entities.Customer;
import com.crm.wm.entities.Invoice;
import com.crm.wm.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    // Other dependencies...

    @Transactional
    public Invoice generateInvoiceManually(Customer customer, Date invoiceDate) {
        // Check if an invoice for the given month already exists
        if (invoiceRepository.existsByCustomerAndInvoiceDate(customer, invoiceDate)) {
            throw new InvoiceGenerationException("Invoice already generated for the specified month.");
        }

        // Logic to generate the invoice manually
        Invoice invoice = new Invoice();
        invoice.setCustomer(customer);
        invoice.setInvoiceDate(invoiceDate);
        // Additional logic to set other details and calculate the total amount

        // Save the manually generated invoice
        return invoiceRepository.save(invoice);
    }

    public boolean isInvoiceGeneratedForMonth(Customer customer, Date invoiceDate) {
        return invoiceRepository.existsByCustomerAndInvoiceDate(customer, invoiceDate);
    }
}

