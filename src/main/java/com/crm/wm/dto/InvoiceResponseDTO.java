package com.crm.wm.dto;

import com.crm.wm.dto.serialize_deserialize.CustomDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class InvoiceResponseDTO {

    private Long invoiceId;
    private Date invoiceDate;
    private Double totalAmount;
    private Long customerId;
    private Long employeeId;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private List<String> months;
    private Long municipalityId;
    private Boolean isPaid;

    // Constructor
    public InvoiceResponseDTO(Long invoiceId, Date invoiceDate, Double totalAmount, Long customerId, Long employeeId, List<String> months, Long municipalityId, Boolean isPaid) {
        this.invoiceId = invoiceId;
        this.invoiceDate = invoiceDate;
        this.totalAmount = totalAmount;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.months = months;
        this.municipalityId = municipalityId;
        this.isPaid = isPaid;
    }
}
