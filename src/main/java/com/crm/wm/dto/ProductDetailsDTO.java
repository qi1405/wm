package com.crm.wm.dto;

import lombok.Data;

@Data
public class ProductDetailsDTO {
    private String productName;
    private Double productPrice;
    private String productDescription;
    private Integer productQuantity;

    // Constructors, getters, and setters
}

