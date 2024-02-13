package com.crm.wm.dto;

import com.crm.wm.entities.Company;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyDTO {

    private Long companyID;

    private String companyName;

    public CompanyDTO(Company company) {
        this.companyID = company.getCompanyID();
        this.companyName = company.getCompanyName();
    }
}
