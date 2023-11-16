package com.crm.wm.entity;

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
@Table(name = "Municipalities")
@Data
public class Municipality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long municipalityID;

    private String municipalityName;

    @OneToMany(mappedBy = "municipality")
    private List<Customer> customers;

    @OneToMany(mappedBy = "municipality")
    private List<Product> products;

    // Other fields, constructors, getters, setters
}
