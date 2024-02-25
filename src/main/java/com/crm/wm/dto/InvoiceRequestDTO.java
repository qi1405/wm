package com.crm.wm.dto;

import com.crm.wm.dto.serialize_deserialize.CustomDateDeserializer;
import com.crm.wm.dto.serialize_deserialize.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class InvoiceRequestDTO {
    private Long customerId;
    private Long employeeId;
    private Long municipalityId;
//    @JsonSerialize(using = CustomDateSerializer.class)
//    @JsonDeserialize(using = CustomDateDeserializer.class)
    private String months;
    private List<InvoiceItemRequestDTO> additionalProducts;
    private Boolean isPaid;

    // Getters and setters

//    public List<ProductIdDTO> getAdditionalProducts() {
//        return additionalProducts;
//    }
//
//    public void setAdditionalProducts(List<ProductIdDTO> additionalProducts) {
//        this.additionalProducts = additionalProducts;
//    }
}