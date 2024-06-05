package com.crm.wm.services;

import com.crm.wm.dto.*;
import com.crm.wm.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    private final EntityManager entityManager;

    @Autowired
    public InvoiceService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

//    @Transactional(readOnly = true)
//    public List<InvoiceResponseDTO> getAllInvoices() {
//        String jpql = "SELECT NEW com.crm.wm.dto.InvoiceResponseDTO(i.invoiceID, i.invoiceDate, i.totalAmount, i.customer.customerID, i.customer.firstName, i.employee.employeeID, i.month, i.municipality.municipalityID, i.isPaid) FROM Invoice i";
//        TypedQuery<InvoiceResponseDTO> query = entityManager.createQuery(jpql, InvoiceResponseDTO.class);
//        return query.getResultList();
//    }

    @Transactional(readOnly = true)
    public List<InvoiceResponseDTO> getInvoicesByCustomer(Long customerId) {
        String jpql = "SELECT NEW com.crm.wm.dto.InvoiceResponseDTO(i.invoiceID, i.invoiceDate, i.totalAmount, i.customer.customerID, i.customer.firstName, i.employee.employeeID, i.month, i.municipality.municipalityID, i.isPaid) FROM Invoice i WHERE i.customer.customerID = :customerId";
        TypedQuery<InvoiceResponseDTO> query = entityManager.createQuery(jpql, InvoiceResponseDTO.class);
        query.setParameter("customerId", customerId);
        return query.getResultList();
    }

    public InvoiceDetailsDTO getInvoiceDetails(Long invoiceId) {
        Invoice invoice = entityManager.find(Invoice.class, invoiceId);

        if (invoice != null) {
            InvoiceDetailsDTO invoiceDetailsDTO = new InvoiceDetailsDTO();
            // Populate invoice details

            // Populate customer details
            Customer customer = invoice.getCustomer();
            invoiceDetailsDTO.setCustomerId(customer.getCustomerID());
            invoiceDetailsDTO.setCustomerFirstName(customer.getFirstName());
            invoiceDetailsDTO.setCustomerLastName(customer.getLastName());

            // Populate employee details
            Employee employee = invoice.getEmployee();
            invoiceDetailsDTO.setEmployeeId(employee.getEmployeeID());
            invoiceDetailsDTO.setEmployeeFirstName(employee.getFirstName());
            invoiceDetailsDTO.setEmployeeLastName(employee.getLastName());

            // Populate municipality details
            Municipality municipality = invoice.getMunicipality();
            invoiceDetailsDTO.setMunicipalityId(municipality.getMunicipalityID());
            invoiceDetailsDTO.setMunicipalityName(municipality.getMunicipalityName());

            // Populate product details
            List<InvoiceItem> invoiceItems = invoice.getInvoiceItems();
            if (invoiceItems != null && !invoiceItems.isEmpty()) {
                List<ProductDetailsDTO> productDetailsList = new ArrayList<>();

                for (InvoiceItem invoiceItem : invoiceItems) {
                    Product product = invoiceItem.getProduct();

                    ProductDetailsDTO productDetailsDTO = new ProductDetailsDTO();
                    productDetailsDTO.setProductName(product.getProductName());
                    productDetailsDTO.setProductPrice(product.getPrice());
                    productDetailsDTO.setProductDescription(product.getDescription());
                    productDetailsDTO.setProductQuantity(invoiceItem.getQuantity());
                    productDetailsDTO.setTotalPrice(invoiceItem.getTotalPrice());

                    productDetailsList.add(productDetailsDTO);
                }

                invoiceDetailsDTO.setProductDetailsList(productDetailsList);
            }

            // Populate company details if applicable
            if (customer.getCustomerType() == CustomerType.COMPANY && customer.getCompany() != null) {
                invoiceDetailsDTO.setCompanyName(customer.getCompany().getCompanyName());
            }

            // Populate other invoice details
            invoiceDetailsDTO.setInvoiceId(invoice.getInvoiceID());
            invoiceDetailsDTO.setInvoiceDate(invoice.getInvoiceDate());
            invoiceDetailsDTO.setTotalAmount(invoice.getTotalAmount());
            invoiceDetailsDTO.setMonth(invoice.getMonth());
            invoiceDetailsDTO.setIsPaid(invoice.getIsPaid());

            return invoiceDetailsDTO;
        } else {
            // Handle the case where the invoice with the given ID is not found
            throw new IllegalArgumentException("Invoice with ID " + invoiceId + " not found");
        }
    }

    public InvoiceDetailsDTO getInvoiceDetailsReport(Long invoiceId) throws SQLException {
        Invoice invoice = entityManager.find(Invoice.class, invoiceId);

        if (invoice != null) {
            InvoiceDetailsDTO invoiceDetailsDTO = new InvoiceDetailsDTO();
            // Populate invoice details

            // Populate customer details
            Customer customer = invoice.getCustomer();
            invoiceDetailsDTO.setCustomerId(customer.getCustomerID());
            invoiceDetailsDTO.setCustomerFirstName(customer.getFirstName());
            invoiceDetailsDTO.setCustomerLastName(customer.getLastName());

            // Populate employee details
            Employee employee = invoice.getEmployee();
            invoiceDetailsDTO.setEmployeeId(employee.getEmployeeID());
            invoiceDetailsDTO.setEmployeeFirstName(employee.getFirstName());
            invoiceDetailsDTO.setEmployeeLastName(employee.getLastName());

            // Populate municipality details
            Municipality municipality = invoice.getMunicipality();
            invoiceDetailsDTO.setMunicipalityId(municipality.getMunicipalityID());
            invoiceDetailsDTO.setMunicipalityName(municipality.getMunicipalityName());

            // Populate product details
            List<InvoiceItem> invoiceItems = invoice.getInvoiceItems();
            if (invoiceItems != null && !invoiceItems.isEmpty()) {
                List<ProductDetailsDTO> productDetailsList = new ArrayList<>();

                for (InvoiceItem invoiceItem : invoiceItems) {
                    Product product = invoiceItem.getProduct();

                    ProductDetailsDTO productDetailsDTO = new ProductDetailsDTO();
                    productDetailsDTO.setProductName(product.getProductName());
                    productDetailsDTO.setProductPrice(product.getPrice());
                    productDetailsDTO.setProductDescription(product.getDescription());
                    productDetailsDTO.setProductQuantity(invoiceItem.getQuantity());
                    productDetailsDTO.setTotalPrice(invoiceItem.getTotalPrice());

                    productDetailsList.add(productDetailsDTO);
                }

                invoiceDetailsDTO.setProductDetailsList(productDetailsList);
            }

            // Populate company details if applicable
            if (customer.getCustomerType() == CustomerType.COMPANY && customer.getCompany() != null) {
                invoiceDetailsDTO.setCompanyName(customer.getCompany().getCompanyName());
            }

            // Populate other invoice details
            invoiceDetailsDTO.setInvoiceId(invoice.getInvoiceID());
            invoiceDetailsDTO.setInvoiceDate(invoice.getInvoiceDate());
            invoiceDetailsDTO.setTotalAmount(invoice.getTotalAmount());
            invoiceDetailsDTO.setMonth(invoice.getMonth());
            invoiceDetailsDTO.setIsPaid(invoice.getIsPaid());

            return invoiceDetailsDTO;
        } else {
            // Handle the case where the invoice with the given ID is not found
            throw new IllegalArgumentException("Invoice with ID " + invoiceId + " not found");
        }
    }

    //Generate invoices manually with the default product associated to the customer, + -->
    //--> with the possibility to add additional products or the same product more times if needed + -->
    //--> multiple months.
    @Transactional
    public List<InvoiceResponseDTO> generateInvoices(List<InvoiceRequestDTO> requestDTOs) {
        List<InvoiceResponseDTO> responseDTOs = new ArrayList<>();

        for (InvoiceRequestDTO requestDTO : requestDTOs) {
            Customer customer = entityManager.find(Customer.class, requestDTO.getCustomerId());

            // Check if an invoice already exists for the given customer and month
            if (invoiceExistsForCustomerAndMonth(customer, requestDTO.getMonths())) {
                // Handle the case where an invoice already exists
                throw new IllegalArgumentException("Invoice already exists for customer " + requestDTO.getCustomerId() + " in month " + requestDTO.getMonths());
            }

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
            invoice.setMonth(requestDTO.getMonths());
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
                    customer.getFirstName(),
                    requestDTO.getEmployeeId(),
                    requestDTO.getMonths(),
                    requestDTO.getMunicipalityId(),
                    invoice.getIsPaid()
            ));
        }

        return responseDTOs;
    }

    @Transactional
    public List<InvoiceResponseDTO> generateInvoicesWithoutDefault(List<InvoiceRequestWithoutDefaultDTO> requestDTOs) {
        List<InvoiceResponseDTO> responseDTOs = new ArrayList<>();

        for (InvoiceRequestWithoutDefaultDTO requestDTO : requestDTOs) {
            Customer customer = entityManager.find(Customer.class, requestDTO.getCustomerId());

            // Check if the request contains at least one product
            if (requestDTO.getProducts() == null || requestDTO.getProducts().isEmpty()) {
                throw new IllegalArgumentException("At least one product should be involved for each invoice request.");
            }

            // Fetch products based on productIds
            List<Product> products = entityManager
                    .createQuery("SELECT p FROM Product p WHERE p.productID IN :productIds", Product.class)
                    .setParameter("productIds", requestDTO.getProducts().stream()
                            .map(InvoiceItemRequestDTO::getProductId)
                            .collect(Collectors.toList()))
                    .getResultList();

            // Calculate the total amount based on the products
            Double totalAmount = calculateTotalAmountRandInv(products, requestDTO.getProducts());

            // Create and save the invoice with the current date and time
            Invoice invoice = new Invoice();
            invoice.setCustomer(customer);
            invoice.setEmployee(entityManager.find(Employee.class, requestDTO.getEmployeeId()));
            invoice.setMunicipality(entityManager.find(Municipality.class, requestDTO.getMunicipalityId()));
            invoice.setInvoiceDate(new Date());
            invoice.setTotalAmount(totalAmount);
            invoice.setIsPaid(requestDTO.getIsPaid() != null ? requestDTO.getIsPaid() : false);

            // Create and associate InvoiceItem entities for products
            List<InvoiceItem> productItems = createInvoiceItemsWithQuantity(products, requestDTO.getProducts());
            productItems.forEach(item -> item.setInvoice(invoice));

            invoice.setInvoiceItems(productItems);

            entityManager.persist(invoice);

            responseDTOs.add(new InvoiceResponseDTO(
                    invoice.getInvoiceID(),
                    invoice.getInvoiceDate(),
                    totalAmount,
                    customer.getCustomerID(),
                    customer.getFirstName(),
                    requestDTO.getEmployeeId(),
                    null,  // No month for this type of invoice
                    requestDTO.getMunicipalityId(),
                    invoice.getIsPaid()
            ));
        }

        return responseDTOs;
    }

    private List<InvoiceItem> createInvoiceItemsWithQuantity(List<Product> products, List<InvoiceItemRequestDTO> productQuantityDTOs) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("At least one product should be involved for each invoice request.");
        }

        List<InvoiceItem> invoiceItems = new ArrayList<>();

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            Integer quantity = productQuantityDTOs.get(i).getQuantity();
            InvoiceItem invoiceItem = new InvoiceItem(product, quantity != null ? quantity : 1);
            invoiceItems.add(invoiceItem);
        }

        return invoiceItems;
    }

    private Double calculateTotalAmountRandInv(List<Product> products, List<InvoiceItemRequestDTO> productQuantityDTOs) {
        // Calculate total amount by summing up the prices of products multiplied by their quantities
        double totalAmount = 0.0;
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            Integer quantity = productQuantityDTOs.get(i).getQuantity();
            totalAmount += product.getPrice() * (quantity != null ? quantity : 1);
        }
        return totalAmount;
    }

    private List<InvoiceItem> createInvoiceItems(List<InvoiceItemRequestDTO> additionalProductDTOs) {
        if (additionalProductDTOs == null || additionalProductDTOs.isEmpty()) {
            return Collections.emptyList();
        }

        List<InvoiceItem> invoiceItems = new ArrayList<>();

        for (InvoiceItemRequestDTO productDTO : additionalProductDTOs) {
            Product additionalProduct = entityManager.find(Product.class, productDTO.getProductId());
            if (additionalProduct != null) {
                Integer quantity = productDTO.getQuantity() != null ? productDTO.getQuantity() : 1;
                InvoiceItem invoiceItem = new InvoiceItem(additionalProduct, quantity);
                invoiceItems.add(invoiceItem);
            }
        }

        return invoiceItems;
    }

    private Double calculateTotalAmount(Double defaultProductPrice, List<Product> additionalProducts, List<InvoiceItemRequestDTO> additionalProductDTOs) {
        // Calculate total amount by starting with the default product price
        double totalAmount = defaultProductPrice;

        // Add the prices of additional products (if provided)
        if (additionalProducts != null) {
            for (Product product : additionalProducts) {
                totalAmount = product.getPrice();
            }
        }

        // Include additional products specified in the request by IDs (if provided)
        if (additionalProductDTOs != null) {
            for (InvoiceItemRequestDTO productDTO : additionalProductDTOs) {
                Product additionalProduct = entityManager.find(Product.class, productDTO.getProductId());
                if (additionalProduct != null) {
                    Integer quantity = productDTO.getQuantity() != null ? productDTO.getQuantity() : 1;
                    totalAmount += additionalProduct.getPrice() * quantity;
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

    private boolean invoiceExistsForCustomerAndMonth(Customer customer, String month) {
        String jpql = "SELECT COUNT(i) FROM Invoice i WHERE i.customer = :customer AND i.month = :month";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("customer", customer);
        query.setParameter("month", month);
        return query.getSingleResult() > 0;
    }
}