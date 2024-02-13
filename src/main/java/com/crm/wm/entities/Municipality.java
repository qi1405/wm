package com.crm.wm.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Getter
@Setter
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

    public Municipality(long municipalityID) {
        this.municipalityID = municipalityID;
    }

    // Other fields, constructors, getters, setters
}
