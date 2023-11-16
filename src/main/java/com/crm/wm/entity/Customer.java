package com.crm.wm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


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

    // Other fields, constructors, getters, setters

}