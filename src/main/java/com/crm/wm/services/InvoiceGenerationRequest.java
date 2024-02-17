package com.crm.wm.dto;

import lombok.Data;

import java.util.List;

@Data
public class InvoiceGenerationRequest {

    private Long customerId;
    private Long employeeId;
    private List<Long> productIds;
}
