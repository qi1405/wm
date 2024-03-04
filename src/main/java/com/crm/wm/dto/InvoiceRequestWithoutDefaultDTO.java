package com.crm.wm.dto;

import lombok.Data;
import java.util.List;

@Data
public class InvoiceRequestWithoutDefaultDTO {
    private Long customerId;
    private Long employeeId;
    private Long municipalityId;
    private List<InvoiceItemRequestDTO> invoiceItemRequestDTOs;
    private Boolean isPaid;
}
