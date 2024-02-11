package com.crm.wm.services;

import com.crm.wm.entities.Product;

public class ProductWithQuantity {

    private Product product;
    private int quantity;

    public ProductWithQuantity(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Getters and setters
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
