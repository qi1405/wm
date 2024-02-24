package com.crm.wm.dto;

import com.crm.wm.dto.serialize_deserialize.CustomDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import java.util.Date;

@Data
public class InvoiceResponseDTO {

    private Long invoiceId;
    private Date invoiceDate;
    private Double totalAmount;
    private Long customerId;
    private Long employeeId;
    private String month;
    private Long municipalityId;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Boolean isPaid;

    // Constructor
    public InvoiceResponseDTO(Long invoiceId, Date invoiceDate, Double totalAmount, Long customerId, Long employeeId, String month, Long municipalityId, boolean isPaid) {
        this.invoiceId = invoiceId;
//        this.invoiceDate = new Date(invoiceDate.getTime()); // Convert Timestamp to Date
        this.invoiceDate = invoiceDate;
        this.totalAmount = totalAmount;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.month = month;
        this.municipalityId = municipalityId;
        this.isPaid = isPaid;
    }
}
