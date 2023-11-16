package com.crm.wm.repository;

import com.crm.wm.entity.Municipality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcMunicipalityRepository implements MunicipalityRepository {

    private static final String INSERT_MUNICIPALITY_QUERY = "INSERT INTO Municipalities (municipality_name) VALUES(?)";
    private static final String UPDATE_MUNICIPALITY_BY_ID_QUERY = "UPDATE Municipalities SET municipality_name=? WHERE municipalityID=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Municipality create(Municipality municipality) {
        jdbcTemplate.update(INSERT_MUNICIPALITY_QUERY, municipality.getMunicipalityName());
        return municipality;
    }

    @Override
    public Municipality update(Municipality municipality) {
        jdbcTemplate.update(UPDATE_MUNICIPALITY_BY_ID_QUERY, municipality.getMunicipalityName(), municipality.getMunicipalityID());
        return municipality;
    }
}
