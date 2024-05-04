package com.crm.wm.controllers;

import com.crm.wm.dto.CustomerDTO;
import com.crm.wm.dto.CustomerProductAssociationRequest;
import com.crm.wm.dto.CustomerWithProductsDTO;
import com.crm.wm.entities.*;
import com.crm.wm.repository.CustomerRepository;
import com.crm.wm.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    // Add CRUD operations and custom endpoints
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(@RequestBody CustomerProductAssociationRequest request) {
        Customer customer = request.getCustomer();
        List<Long> productIds = request.getProductIds();

        // Fetch the customer from the request
        if (customer == null) {
            return ResponseEntity.badRequest().body("Customer details are required.");
        }

        // Check if customer type is INDIVIDUAL and there are company details
        if (customer.getCustomerType() == CustomerType.INDIVIDUAL && customer.getCompany() != null) {
            return ResponseEntity.badRequest().body("Individual customer cannot have company details.");
        }

        // Fetch products by their IDs
        List<Product> products = productRepository.findAllById(productIds);

        // Associate the products with the customer
        customer.setProducts(products);
        customerRepository.save(customer);

// Check if customer's municipality matches the municipality of the products
        for (Product product : products) {
            // Log the municipalities for debugging
//            System.out.println("Customer's municipality: " + customer.getMunicipality());
//            System.out.println("Product's municipality: " + product.getMunicipality());

            Municipality customerMunicipality = customer.getMunicipality();
            Municipality productMunicipality = product.getMunicipality();

            // Check if the municipalities are null or if their IDs are not equal
            if (customerMunicipality == null || productMunicipality == null ||
                    !Objects.equals(customerMunicipality.getMunicipalityID(), productMunicipality.getMunicipalityID())) {
                return ResponseEntity.badRequest().body("Customer's municipality must match the municipality of the products.");
            }

            // Check if the municipality names are not null and not equal
            if (customerMunicipality.getMunicipalityName() != null &&
                    !customerMunicipality.getMunicipalityName().equals(productMunicipality.getMunicipalityName())) {
                return ResponseEntity.badRequest().body("Customer's municipality name must match the municipality name of the products.");
            }
        }

        return ResponseEntity.ok("Customer saved and all products associated with the customer successfully.");
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<CustomerDTO>> getAllCustomersWithProducts() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOs = customers.stream().map(CustomerDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(customerDTOs);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/{customerID}")
    public ResponseEntity<CustomerWithProductsDTO> getCustomerWithProducts(@PathVariable Long customerID) {
        // Find the customer by ID
        Optional<Customer> customerOptional = customerRepository.findById(customerID);
        if (customerOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Customer customer = customerOptional.get();

        // Fetch associated products for the customer
        List<Product> products = customer.getProducts();

        // Map the customer and products to DTOs
        CustomerWithProductsDTO customerWithProductsDTO = new CustomerWithProductsDTO(customer, products);

        return ResponseEntity.ok(customerWithProductsDTO);
    }

    // Update customer details

        @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
        @PutMapping("/update/{id}")
        public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody CustomerProductAssociationRequest request) {
            // Find the customer by ID
            Optional<Customer> customerOptional = customerRepository.findById(id);
            if (customerOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Customer customer = customerOptional.get();

            // Update the customer fields from the request
            Customer updatedCustomer = request.getCustomer();
            if (updatedCustomer == null) {
                return ResponseEntity.badRequest().body("Customer details are required.");
            }
            customer.setFirstName(updatedCustomer.getFirstName());
            customer.setLastName(updatedCustomer.getLastName());
            customer.setEmail(updatedCustomer.getEmail());
            customer.setPhoneNumber(updatedCustomer.getPhoneNumber());
            customer.setAddress(updatedCustomer.getAddress());
            customer.setMunicipality(updatedCustomer.getMunicipality());
            customer.setCustomerType(updatedCustomer.getCustomerType());
            customer.setCompany(updatedCustomer.getCompany());

            // Check if customer type is INDIVIDUAL and there are company details
            if (customer.getCustomerType() == CustomerType.INDIVIDUAL && customer.getCompany() != null) {
                return ResponseEntity.badRequest().body("Individual customer cannot have company details.");
            }

            // Fetch products by their IDs
            List<Long> productIds = request.getProductIds();
            List<Product> products = productRepository.findAllById(productIds);

            // Associate the products with the customer
            customer.setProducts(products);
            customerRepository.save(customer);

            // Check if customer's municipality matches the municipality of the products
            for (Product product : products) {
                // Log the municipalities for debugging
//            System.out.println("Customer's municipality: " + customer.getMunicipality());
//            System.out.println("Product's municipality: " + product.getMunicipality());

                Municipality customerMunicipality = customer.getMunicipality();
                Municipality productMunicipality = product.getMunicipality();

                // Check if the municipalities are null or if their IDs are not equal
                if (customerMunicipality == null || productMunicipality == null ||
                        !Objects.equals(customerMunicipality.getMunicipalityID(), productMunicipality.getMunicipalityID())) {
                    return ResponseEntity.badRequest().body("Customer's municipality must match the municipality of the products.");
                }

                // Check if the municipality names are not null and not equal
                if (customerMunicipality.getMunicipalityName() != null &&
                        !customerMunicipality.getMunicipalityName().equals(productMunicipality.getMunicipalityName())) {
                    return ResponseEntity.badRequest().body("Customer's municipality name must match the municipality name of the products.");
                }
            }

            return ResponseEntity.ok("Customer updated and all products associated with the customer successfully.");
        }


        // Add more endpoints as needed
    }