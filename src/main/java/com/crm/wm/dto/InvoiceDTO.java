//package com.crm.wm.dto;
//
//import com.crm.wm.entities.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.Date;
//import java.util.List;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class InvoiceDTO {
//
//    private Long invoiceID;
//    private Date invoiceDate;
//    private EmployeeDTO employee;
//    private CustomerDTO customer;
//    private MunicipalityDTO municipality;
//    private Double totalAmount;
//    private List<InvoiceItemDTO> invoiceItems;
//
//    public InvoiceDTO(Invoice invoice) {
//        this.invoiceID = invoice.getInvoiceID();
//        this.invoiceDate = invoice.getInvoiceDate();
//        this.employee = new EmployeeDTO(invoice.getEmployee());
//        this.customer = new CustomerDTO(invoice.getCustomer());
//        this.municipality = new MunicipalityDTO(invoice.getCustomer().getMunicipality());
//        this.totalAmount = invoice.getTotalAmount();
//        // Assuming there is a method to convert InvoiceItems to DTO
//        this.invoiceItems = InvoiceItemDTO.convertToDTOList(invoice.getInvoiceItems());
//    }
//}