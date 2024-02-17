package com.crm.wm.dto;

import com.crm.wm.entities.Company;
import com.crm.wm.entities.Customer;
import com.crm.wm.entities.CustomerType;
import com.crm.wm.entities.Municipality;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO {

    private Long customerID;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private CustomerType customerType;
    private CompanyDTO company;
    private MunicipalityDTO municipality;

    public CustomerDTO(Customer customer) {
        this.customerID = customer.getCustomerID();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.email = customer.getEmail();
        this.phoneNumber = customer.getPhoneNumber();
        this.address = customer.getAddress();
        this.customerType = customer.getCustomerType();
        if (customer.getCustomerType() == CustomerType.COMPANY && customer.getCompany() != null) {
            this.company = new CompanyDTO(customer.getCompany());}
        this.municipality = new MunicipalityDTO(customer.getMunicipality());
    }
}
