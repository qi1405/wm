package com.crm.wm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class InvoiceDetailsDTO {

    private Long invoiceId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date invoiceDate;

    private Double totalAmount;
    private Long customerId;
    private Long employeeId;
    private String month;
    private Long municipalityId;
    private Boolean isPaid;

    private String customerFirstName;
    private String customerLastName;

    private String employeeFirstName;
    private String employeeLastName;

    private String municipalityName;

    // Additional fields for company details if applicable
    private String companyName;

    private List<ProductDetailsDTO> productDetailsList;

    // Constructors, getters, and setters

}
