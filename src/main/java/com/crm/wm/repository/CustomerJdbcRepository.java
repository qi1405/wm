package com.crm.wm.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerJdbcRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Add JDBC queries if needed
}
