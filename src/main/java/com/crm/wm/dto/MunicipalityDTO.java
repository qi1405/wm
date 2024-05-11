package com.crm.wm.dto;

import com.crm.wm.entities.Municipality;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MunicipalityDTO {
    @JsonProperty("municipalityID")
    private Long municipalityID;

    @JsonProperty("municipalityName")
    private String municipalityName;

    public MunicipalityDTO(Municipality municipality) {
        if (municipality != null) {
            this.municipalityID = municipality.getMunicipalityID();
            this.municipalityName = municipality.getMunicipalityName();
        } else {
            this.municipalityID = null;
            this.municipalityName = null;
        }
    }

    // Constructors, getters, setters
}

