package com.crm.wm.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class InvoiceRequestDTO {
    private Long customerId;
    private Long employeeId;
    private Long municipalityId;
    private Date month;
    private Date invoiceDate; // Add this field
    private List<Long> additionalProducts; // Add this field for additional product IDs
}