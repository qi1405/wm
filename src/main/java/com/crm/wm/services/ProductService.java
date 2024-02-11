package com.crm.wm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.wm.entities.Customer;
import com.crm.wm.entities.Product;
import com.crm.wm.repository.ProductRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Other dependencies...

    public Map<Long, Double> getPricesForProductsAssociatedWithCustomer(Customer customer) {
        // Assuming you have a method in the ProductRepository to fetch products associated with a customer
        List<Product> products = productRepository.findByCustomer(customer);

        // Assuming each product has a price field
        Map<Long, Double> productPrices = new HashMap<>();
        for (Product product : products) {
            productPrices.put(product.getProductID(), product.getPrice());
        }
        return productPrices;
    }

    // Other methods...
}

