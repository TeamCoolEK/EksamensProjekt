package org.example.coolplanner.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AnsvarligRepository {

    private final JdbcTemplate jdbcTemplate;

    public AnsvarligRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


}
