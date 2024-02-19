package com.crm.wm.services;

import com.crm.wm.dto.InvoiceRequestDTO;
import com.crm.wm.dto.InvoiceResponseDTO;
import com.crm.wm.entities.*;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public InvoiceResponseDTO generateInvoice(InvoiceRequestDTO requestDTO) {
        Customer customer = entityManager.find(Customer.class, requestDTO.getCustomerId());

        // Fetch the default product associated with the customer
        String nativeQuery = "SELECT p.* FROM Customer_Product cp " +
                "JOIN Products p ON cp.ProductID = p.ProductID " +
                "WHERE cp.CustomerID = :customerId";
        Product defaultProduct = (Product) entityManager.createNativeQuery(nativeQuery, Product.class)
                .setParameter("customerId", requestDTO.getCustomerId())
                .getSingleResult();

        // Calculate the total amount including the default product and additional products (if provided)
        Double totalAmount = calculateTotalAmount(defaultProduct.getPrice(), customer.getProducts(), requestDTO.getAdditionalProducts());

        // Create and save the invoice with the current date and time
        Invoice invoice = new Invoice();
        invoice.setCustomer(customer);
        invoice.setEmployee(entityManager.find(Employee.class, requestDTO.getEmployeeId()));
        invoice.setMunicipality(entityManager.find(Municipality.class, requestDTO.getMunicipalityId()));
        invoice.setInvoiceDate(new Date()); // Set the invoice date to the current date and time
        invoice.setMonth(requestDTO.getMonth());
        invoice.setTotalAmount(totalAmount);
        entityManager.persist(invoice);

        // Return the response DTO
        InvoiceResponseDTO responseDTO = new InvoiceResponseDTO();
        responseDTO.setInvoiceId(invoice.getInvoiceID());
        responseDTO.setTotalAmount(totalAmount);

        return responseDTO;
    }

    private Double calculateTotalAmount(Double defaultProductPrice, List<Product> additionalProducts, List<Long> additionalProductIds) {
        // Calculate total amount by summing default product price and additional products (if provided)
        double totalAmount = defaultProductPrice;

        if (additionalProducts != null) {
            for (Product product : additionalProducts) {
                totalAmount += product.getPrice();
            }
        }

        // Include additional products specified in the request by IDs (if provided)
        if (additionalProductIds != null) {
            for (Long productId : additionalProductIds) {
                Product additionalProduct = entityManager.find(Product.class, productId);
                if (additionalProduct != null) {
                    totalAmount += additionalProduct.getPrice();
                }
            }
        }

        return totalAmount;
    }
}
