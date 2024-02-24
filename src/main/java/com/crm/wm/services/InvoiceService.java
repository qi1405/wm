package com.crm.wm.services;

import com.crm.wm.dto.InvoiceRequestDTO;
import com.crm.wm.dto.InvoiceResponseDTO;
import com.crm.wm.dto.ProductIdDTO;
import com.crm.wm.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<InvoiceResponseDTO> getAllInvoices() {
        String jpql = "SELECT NEW com.crm.wm.dto.InvoiceResponseDTO(i.invoiceID, i.invoiceDate, i.totalAmount, i.customer.customerID, i.employee.employeeID, i.month, i.municipality.municipalityID, i.isPaid) FROM Invoice i";
        TypedQuery<InvoiceResponseDTO> query = entityManager.createQuery(jpql, InvoiceResponseDTO.class);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    public List<InvoiceResponseDTO> getInvoicesByCustomer(Long customerId) {
        String jpql = "SELECT NEW com.crm.wm.dto.InvoiceResponseDTO(i.invoiceID, i.invoiceDate, i.totalAmount, i.customer.customerID, i.employee.employeeID, i.month, i.municipality.municipalityID, i.isPaid) FROM Invoice i WHERE i.customer.customerID = :customerId";
        TypedQuery<InvoiceResponseDTO> query = entityManager.createQuery(jpql, InvoiceResponseDTO.class);
        query.setParameter("customerId", customerId);
        return query.getResultList();
    }

    @Transactional
    public List<InvoiceResponseDTO> generateInvoices(List<InvoiceRequestDTO> requestDTOs) {
        List<InvoiceResponseDTO> responseDTOs = new ArrayList<>();

        for (InvoiceRequestDTO requestDTO : requestDTOs) {
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
            invoice.setInvoiceDate(new Date());
            invoice.setMonths(requestDTO.getMonths()); // Assuming it's a List<String> of months
            invoice.setTotalAmount(totalAmount);
            invoice.setIsPaid(false);  // Assuming it's not paid initially

            // Create and associate InvoiceItem entity for the default product
            InvoiceItem defaultProductItem = new InvoiceItem(defaultProduct, 1); // Assuming quantity is 1 for the default product
            defaultProductItem.setInvoice(invoice);

            // Create and associate InvoiceItem entities for additional products
            List<InvoiceItem> additionalProductItems = createInvoiceItems(requestDTO.getAdditionalProducts());
            additionalProductItems.forEach(item -> item.setInvoice(invoice));

            List<InvoiceItem> allInvoiceItems = new ArrayList<>();
            allInvoiceItems.add(defaultProductItem);
            allInvoiceItems.addAll(additionalProductItems);

            invoice.setInvoiceItems(allInvoiceItems);

            entityManager.persist(invoice);

            responseDTOs.add(new InvoiceResponseDTO(
                    invoice.getInvoiceID(),
                    invoice.getInvoiceDate(),
                    totalAmount,
                    customer.getCustomerID(),
                    requestDTO.getEmployeeId(),
                    requestDTO.getMonths(),
                    requestDTO.getMunicipalityId(),
                    invoice.getIsPaid()
            ));
        }

        return responseDTOs;
    }

    private List<InvoiceItem> createInvoiceItems(List<ProductIdDTO> additionalProductIds) {
        if (additionalProductIds == null) {
            return List.of();  // Return an empty list if no additional products
        }

        List<InvoiceItem> invoiceItems = new ArrayList<>();

        for (ProductIdDTO productIdDTO : additionalProductIds) {
            Product additionalProduct = entityManager.find(Product.class, productIdDTO.getProductId());
            if (additionalProduct != null) {
                InvoiceItem invoiceItem = new InvoiceItem(additionalProduct, 1);  // Assuming quantity is 1
                invoiceItems.add(invoiceItem);
            }
        }

        return invoiceItems;
    }

    private Double calculateTotalAmount(Double defaultProductPrice, List<Product> additionalProducts, List<ProductIdDTO> additionalProductIds) {
        // Calculate total amount by starting with the default product price
        double totalAmount = defaultProductPrice;

        // Add the prices of additional products (if provided)
        if (additionalProducts != null) {
            for (Product product : additionalProducts) {
                totalAmount
                        = product.getPrice();
            }
        }

        // Include additional products specified in the request by IDs (if provided)
        if (additionalProductIds != null) {
            for (ProductIdDTO productIdDTO : additionalProductIds) {
                Product additionalProduct = entityManager.find(Product.class, productIdDTO.getProductId());
                if (additionalProduct != null) {
                    totalAmount += additionalProduct.getPrice();
                }
            }
        }

        return totalAmount;
    }

    @Transactional
    public void updateIsPaidStatus(Long invoiceId, boolean isPaid) {
        Invoice invoice = entityManager.find(Invoice.class, invoiceId);
        if (invoice != null) {
            invoice.setPaid(isPaid);
            // Optionally, you can update other fields or perform additional logic here
            entityManager.merge(invoice);
        } else {
            // Handle the case where the invoice with the given ID is not found
            throw new IllegalArgumentException("Invoice with ID " + invoiceId + " not found");
        }
    }
}
