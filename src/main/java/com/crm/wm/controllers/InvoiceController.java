package com.crm.wm.controllers;

import com.crm.wm.dto.InvoiceRequestDTO;
import com.crm.wm.dto.InvoiceResponseDTO;
import com.crm.wm.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/generate")
    public ResponseEntity<InvoiceResponseDTO> generateInvoice(@RequestBody InvoiceRequestDTO requestDTO) {
        InvoiceResponseDTO responseDTO = invoiceService.generateInvoice(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    // Add more endpoints as needed
}
