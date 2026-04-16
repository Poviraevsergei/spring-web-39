package com.tms.repositories;

import com.tms.model.Security;
import com.tms.util.SqlQueries;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public class SecurityRepository {

    private final JdbcTemplate jdbcTemplate;

    public SecurityRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveSecurity(Security securityInput) {
        jdbcTemplate.update(SqlQueries.SQL_INSERT_SECURITY,
                securityInput.getUsername(),
                securityInput.getPassword(),
                securityInput.getRole().toString(),
                securityInput.getUserId(),
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()));
    }
}
