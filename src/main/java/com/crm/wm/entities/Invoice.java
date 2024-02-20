package com.crm.wm.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Invoices")
@Data
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceID;

    @ManyToOne
    @JoinColumn(name = "CustomerID")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "EmployeeID")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "MunicipalityID")
    private Municipality municipality;

    private Date invoiceDate;

    private Date month;

    private Double totalAmount;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)  // Added cascade for automatic persistence
    private List<InvoiceItem> invoiceItems;

    // Other fields, constructors, getters, setters
}