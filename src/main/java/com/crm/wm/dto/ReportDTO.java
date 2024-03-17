package com.crm.wm.dto;

import lombok.Data;

import java.util.List;

@Data
public class ReportDTO {

    private InvoiceDetailsDTO invoiceDetails;
    private List<ProductDetailsDTO> productDetailsList;

    // Constructors, getters, and setters
}
