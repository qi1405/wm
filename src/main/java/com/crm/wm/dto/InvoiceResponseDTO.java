package com.crm.wm.dto;

import lombok.Data;
import java.util.Date;

@Data
public class InvoiceResponseDTO {

    private Long invoiceId;
    private Date invoiceDate;
    private Double totalAmount;
    private Long customerId;
    private Long employeeId;
    private Date month;
    private Long municipalityId;

    // Constructor
    public InvoiceResponseDTO(Long invoiceId, Date invoiceDate, Double totalAmount, Long customerId, Long employeeId, Date month, Long municipalityId) {
        this.invoiceId = invoiceId;
//        this.invoiceDate = new Date(invoiceDate.getTime()); // Convert Timestamp to Date
        this.invoiceDate = invoiceDate;
        this.totalAmount = totalAmount;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.month = month;
        this.municipalityId = municipalityId;
    }
}
