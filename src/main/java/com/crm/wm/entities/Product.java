package com.crm.wm.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;


@NoArgsConstructor
@ToString
@Entity
@Table(name = "Products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productID;

    private String productName;
    private String description;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "MunicipalityID")
    private Municipality municipality;

    // Other fields, constructors, getters, setters

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Municipality getMunicipality() {
        return municipality;
    }

//    public void setMunicipality(Municipality municipality) {
//        this.municipality = municipality;
//    }

    public Product(Long productID, String productName, String description, Double price, Municipality municipality) {
        this.productID = productID;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.municipality = municipality;
    }
}
