package com.crm.wm.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Customer_Product")
@Data
public class CustomerProduct {

    @Id
    @ManyToOne
    @JoinColumn(name = "CustomerID")
    private Customer customer;

    @Id
    @ManyToOne
    @JoinColumn(name = "ProductID")
    private Product product;

    // Other fields, constructors, getters, setters
}