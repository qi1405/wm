package com.crm.wm.dto;

import lombok.Data;

@Data
public class InvoiceResponseDTO {
    // Create a DTO to represent the response after invoice generation
    private Long invoiceId;
    private Double totalAmount;
    // Add any other fields as needed
}
