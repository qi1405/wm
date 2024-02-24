package com.crm.wm.dto;

import com.crm.wm.entities.Customer;
import java.util.List;

public class CustomerProductAssociationRequest {

    private Customer customer;
    private List<Long> productIds;

    // Constructors, getters, and setters

    public CustomerProductAssociationRequest() {
    }

    public CustomerProductAssociationRequest(Customer customer, List<Long> productIds) {
        this.customer = customer;
        this.productIds = productIds;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }
}
