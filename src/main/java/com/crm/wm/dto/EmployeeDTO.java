package com.crm.wm.dto;

import com.crm.wm.entities.Employee;
import lombok.Data;

@Data
public class EmployeeDTO {

    private Long employeeID;
    private String firstName;
    private String lastName;
    private String username;
    private String email;

    // Other fields, constructors, getters, setters as needed

    public EmployeeDTO(Employee employee) {
        this.employeeID = employee.getId();
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.username = employee.getUsername();
        this.email = employee.getEmail();
    }

    // Additional constructors, getters, setters as needed
}
