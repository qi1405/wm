package com.crm.wm.controllers;

import com.crm.wm.dto.InvoiceRequestDTO;
import com.crm.wm.dto.InvoiceRequestWithoutDefaultDTO;
import com.crm.wm.dto.InvoiceResponseDTO;
import com.crm.wm.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/list")
    public ResponseEntity<List<InvoiceResponseDTO>> getAllInvoices() {
        List<InvoiceResponseDTO> invoices = invoiceService.getAllInvoices();
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/list/customer/{customerId}")
    public ResponseEntity<List<InvoiceResponseDTO>> getInvoicesByCustomer(@PathVariable Long customerId) {
        List<InvoiceResponseDTO> invoices = invoiceService.getInvoicesByCustomer(customerId);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @PostMapping("/generate")
    public ResponseEntity<List<InvoiceResponseDTO>> generateInvoices(@RequestBody List<InvoiceRequestDTO> requestDTOs) {
        List<InvoiceResponseDTO> responseDTOs = invoiceService.generateInvoices(requestDTOs);
        return new ResponseEntity<>(responseDTOs, HttpStatus.CREATED);
    }

    @PutMapping("/update-is-paid/{invoiceId}")
    public ResponseEntity<String> updateIsPaidStatus(@PathVariable Long invoiceId, @RequestBody boolean isPaid) {
        invoiceService.updateIsPaidStatus(invoiceId, isPaid);
        return new ResponseEntity<>("IsPaid status updated successfully", HttpStatus.OK);
    }

    @PostMapping("/generate-without-default")
    public ResponseEntity<List<InvoiceResponseDTO>> generateInvoicesWithoutDefault(@RequestBody List<InvoiceRequestWithoutDefaultDTO> requestDTOs) {
        List<InvoiceResponseDTO> responseDTOs = invoiceService.generateInvoicesWithoutDefault(requestDTOs);
        return new ResponseEntity<>(responseDTOs, HttpStatus.CREATED);
    }

    // Add more endpoints as needed
}
