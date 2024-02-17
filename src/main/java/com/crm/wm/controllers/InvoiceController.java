package com.crm.wm.controllers;

import com.crm.wm.dto.InvoiceDTO;
import com.crm.wm.dto.InvoiceGenerationRequest;
import com.crm.wm.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/generate")
    public ResponseEntity<InvoiceDTO> generateInvoice(@RequestBody InvoiceGenerationRequest request) {
        InvoiceDTO invoiceDTO = invoiceService.generateInvoice(
                request.getCustomerId(),
                request.getEmployeeId(),
                request.getProductIds()
        );
        return ResponseEntity.ok(invoiceDTO);
    }

    // Add more endpoints as needed
}
