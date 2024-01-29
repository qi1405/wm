package com.crm.wm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Companies")
@Data
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyID;

    private String companyName;

    @OneToOne
    @JoinColumn(name = "customerID")
    @JsonIgnore
    private Customer customer;


    // Other fields, constructors, getters, setters

}
