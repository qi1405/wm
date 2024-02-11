package com.crm.wm.controllers;

import com.crm.wm.entities.Product;
import com.crm.wm.services.ProductWithQuantity;

import java.util.ArrayList;
import java.util.List;

public class InvoiceControllerUtil {

    public static List<ProductWithQuantity> mapProductsWithQuantities(List<Product> products, List<Integer> quantities) {
        List<ProductWithQuantity> productsWithQuantity = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            Integer quantity = quantities.get(i);
            productsWithQuantity.add(new ProductWithQuantity(product, quantity));
        }
        return productsWithQuantity;
    }

    public static List<ProductWithQuantity> mapProductsWithQuantities(List<Product> products) {
        List<ProductWithQuantity> productsWithQuantity = new ArrayList<>();
        for (Product product : products) {
            productsWithQuantity.add(new ProductWithQuantity(product, 1));
        }
        return productsWithQuantity;
    }
}
