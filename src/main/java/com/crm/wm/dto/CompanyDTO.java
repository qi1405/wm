package com.crm.wm.dto;

import com.crm.wm.entities.Company;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyDTO {

    @JsonProperty("companyID")
    private Long companyID;
    @JsonProperty("companyName")
    private String companyName;

    public CompanyDTO(Company company) {
        if (company != null) {
            this.companyID = company.getCompanyID();
            this.companyName = company.getCompanyName();
        } else {
            // Set companyID to null or an empty string based on your preference
            this.companyID = null; // or this.companyID = ""; for an empty string
        }
    }
}
