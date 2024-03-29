    package com.crm.wm.entities;

    import com.fasterxml.jackson.annotation.JsonProperty;
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

        private String month;

        private Double totalAmount;

        public void setPaid(Boolean paid) {
            isPaid = paid;
        }

        @JsonProperty("isPaid")
        private Boolean isPaid;

        @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)  // Added cascade for automatic persistence
        private List<InvoiceItem> invoiceItems;

        public void setMonths(List<String> months) {
        }

        // Other fields, constructors, getters, setters
    }