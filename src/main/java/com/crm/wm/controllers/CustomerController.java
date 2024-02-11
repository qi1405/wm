package com.crm.wm.controllers;

import com.crm.wm.dto.CustomerProductAssociationRequest;
import com.crm.wm.entities.Company;
import com.crm.wm.entities.Customer;
import com.crm.wm.entities.CustomerType;
import com.crm.wm.entities.Product;
import com.crm.wm.repository.CustomerJdbcRepository;
import com.crm.wm.repository.CustomerRepository;
import com.crm.wm.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerJdbcRepository customerJdbcRepository;

    @Autowired
    private ProductRepository productRepository;

    // Add CRUD operations and custom endpoints
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping("/")
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
        List<Product> products = productRepository.findAllById(productIds); // Assuming productService is available

        // Associate the products with the customer
        customer.setProducts(products);
        customerRepository.save(customer);

        return ResponseEntity.ok("Customer saved and all products associated with the customer successfully.");
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long customerId, @RequestBody Customer updatedCustomer) {
        Optional<Customer> existingCustomerOptional = customerRepository.findById(customerId);

        if (existingCustomerOptional.isPresent()) {
            Customer existingCustomer = existingCustomerOptional.get();

            // Update customer fields
            existingCustomer.setFirstName(updatedCustomer.getFirstName());
            existingCustomer.setLastName(updatedCustomer.getLastName());
            existingCustomer.setEmail(updatedCustomer.getEmail());
            existingCustomer.setPhoneNumber(updatedCustomer.getPhoneNumber());
            existingCustomer.setAddress(updatedCustomer.getAddress());
            existingCustomer.setMunicipality(updatedCustomer.getMunicipality());
            existingCustomer.setCustomerType(updatedCustomer.getCustomerType());

            // Update associated company information
            Company existingCompany = existingCustomer.getCompany();
            Company updatedCompany = updatedCustomer.getCompany();

            if (existingCompany != null && updatedCompany != null) {
                existingCompany.setCompanyName(updatedCompany.getCompanyName());
                // Update other company fields as needed
            } else if (existingCompany == null && updatedCompany != null) {
                updatedCompany.setCustomer(existingCustomer);
                existingCustomer.setCompany(updatedCompany);
            } else if (existingCompany != null && updatedCompany == null) {
                existingCompany.setCustomer(null);
                existingCustomer.setCompany(null);
            }

            // Save the updated customer
            Customer savedCustomer = customerRepository.save(existingCustomer);

            return new ResponseEntity<>(savedCustomer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    // Add more endpoints as needed
}
