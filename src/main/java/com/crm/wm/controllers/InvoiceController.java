package com.crm.wm.controllers;

import com.crm.wm.dto.InvoiceRequestDTO;
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
    public ResponseEntity<InvoiceResponseDTO> generateInvoice(@RequestBody InvoiceRequestDTO requestDTO) {
        InvoiceResponseDTO responseDTO = invoiceService.generateInvoice(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

//    @PutMapping("/update-is-paid/{invoiceId}")
//    public ResponseEntity<String> updateIsPaidStatus(
//            @PathVariable Long invoiceId,
//            @RequestParam boolean isPaid
//    ) {
//        invoiceService.updateIsPaidStatus(invoiceId, isPaid);
//        return new ResponseEntity<>("IsPaid status updated successfully", HttpStatus.OK);
//    }

    @PutMapping("/update-is-paid/{invoiceId}")
    public ResponseEntity<String> updateIsPaidStatus(@PathVariable Long invoiceId, @RequestBody boolean isPaid) {
        invoiceService.updateIsPaidStatus(invoiceId, isPaid);
        return new ResponseEntity<>("IsPaid status updated successfully", HttpStatus.OK);
    }

    // Add more endpoints as needed
}
