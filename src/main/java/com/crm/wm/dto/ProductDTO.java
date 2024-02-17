package com.crm.wm.dto;

import com.crm.wm.entities.Product;
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

    public ProductDTO(Product product) {
        this.productID = product.getProductID();
        this.productName = product.getProductName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.municipality = new MunicipalityDTO(product.getMunicipality());
    }


    // Constructors, getters, setters
}

