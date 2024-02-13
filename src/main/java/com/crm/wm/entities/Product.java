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

    public Product(Long productID, String productName, String description, Double price, Municipality municipality) {
        this.productID = productID;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.municipality = municipality;
    }

    // Other fields, constructors, getters, setters
}
