package com.crm.wm.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Customers")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerID;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;

    @ManyToOne
    @JoinColumn(name = "MunicipalityID")
    private Municipality municipality;

    @Enumerated(EnumType.STRING)
    private CustomerType customerType;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Company company;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Customer_Product",
            joinColumns = @JoinColumn(name = "CustomerID"),
            inverseJoinColumns = @JoinColumn(name = "ProductID")
    )
    private List<Product> products;

    // Other fields, constructors, getters, setters

    public void setCompany(Company company) {
        if (company == null) {
            if (this.company != null) {
                this.company.setCustomer(null);
            }
        }
        else {
            company.setCustomer(this);
        }
        this.company = company;
    }
}