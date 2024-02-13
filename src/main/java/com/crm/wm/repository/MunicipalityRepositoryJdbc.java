package com.crm.wm.repository;

import com.crm.wm.entities.Municipality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MunicipalityRepositoryJdbc implements MunicipalityRepository {

    private static final String INSERT_MUNICIPALITY_QUERY = "INSERT INTO Municipalities (municipality_name) VALUES(?)";

    private static final String SELECT_ALL_MUNICIPALITY_QUERY = "SELECT * FROM Municipalities";
    private static final String UPDATE_MUNICIPALITY_BY_ID_QUERY = "UPDATE Municipalities SET municipality_name=? WHERE municipalityID=?";

    private static final String DELETE_MUNICIPALITY_BY_ID_QUERY = "DELETE FROM Municipalities WHERE MunicipalityID = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Municipality create(Municipality municipality) {
        // Implement the logic to create a municipality using JdbcTemplate
        jdbcTemplate.update(INSERT_MUNICIPALITY_QUERY, municipality.getMunicipalityName());
        return municipality;
    }

    @Override
    public List<Municipality> readAll() {
        // Implement the logic to read all municipalities using JdbcTemplate
        return jdbcTemplate.query(SELECT_ALL_MUNICIPALITY_QUERY, new BeanPropertyRowMapper<>(Municipality.class));
    }

    @Override
    public Municipality update(Municipality municipality) {
        // Implement the logic to update a municipality using JdbcTemplate
        jdbcTemplate.update(UPDATE_MUNICIPALITY_BY_ID_QUERY, municipality.getMunicipalityName(), municipality.getMunicipalityID());
        return municipality;
    }

    @Override
    public boolean delete(Long municipalityID) {
        // Implement the logic to delete a municipality using JdbcTemplate
        int rowsAffected = jdbcTemplate.update(DELETE_MUNICIPALITY_BY_ID_QUERY, municipalityID);

        return rowsAffected > 0;
    }
}
