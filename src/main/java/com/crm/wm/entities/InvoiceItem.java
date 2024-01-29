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
@Table(name = "InvoiceItems")
@Data
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceItemID;

    @ManyToOne
    @JoinColumn(name = "InvoiceID")
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "ProductID")
    private Product product;

    private Integer quantity;
    private Double totalPrice;

    // Other fields, constructors, getters, setters
}