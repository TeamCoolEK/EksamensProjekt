package org.example.coolplanner.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CoolplannerRepositoryFind {

        private final JdbcTemplate jdbcTemplate;

    public CoolplannerRepositoryFind(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
