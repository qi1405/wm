package com.crm.wm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {
    @JsonProperty("productID")
    private Long productID;

    @JsonProperty("productName")
    private String productName;

    @JsonProperty("description")
    private String description;

    @JsonProperty("price")
    private Double price;

    @JsonProperty("municipality")
    private MunicipalityDTO municipality;

    // Constructors, getters, setters
}

