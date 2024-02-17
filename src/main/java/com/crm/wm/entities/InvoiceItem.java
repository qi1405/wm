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

    private Double price;

    private Integer quantity;

    private Double totalPrice;

    // Other fields, constructors, getters, setters

    public InvoiceItem(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
        this.price = product.getPrice();  // Assuming you have a method getPrice() in your Product class
        this.totalPrice = calculateTotalPrice();
    }

    // Calculate total price directly in the constructor
    private Double calculateTotalPrice() {
        return price * quantity;
    }

    // Other methods
}
