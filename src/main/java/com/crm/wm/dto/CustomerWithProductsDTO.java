package com.crm.wm.dto;

import com.crm.wm.entities.Customer;
import com.crm.wm.entities.CustomerType;
import com.crm.wm.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerWithProductsDTO {

    private Long customerID;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private CustomerType customerType;
    private CompanyDTO company;
    private MunicipalityDTO municipality;
    private List<ProductDTO> products;

    public CustomerWithProductsDTO(Customer customer, List<Product> products) {
        this.customerID = customer.getCustomerID();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.email = customer.getEmail();
        this.phoneNumber = customer.getPhoneNumber();
        this.address = customer.getAddress();
        this.customerType = customer.getCustomerType();

        if (customer.getCustomerType() == CustomerType.COMPANY && customer.getCompany() != null) {
            this.company = new CompanyDTO(customer.getCompany());
        } else {
            this.company = null;
        }

        this.municipality = new MunicipalityDTO(customer.getMunicipality());

        // Map the list of products to ProductDTOs
        this.products = products.stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());
    }
}
