package com.ameda.compulnyx.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Author: kev.Ameda
 */

@Configuration
public class DatabaseInit {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseInit(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @PostConstruct
    public void createDatabaseIfNotExists() {
        String dbName = "compulnyx";

        String checkDbSql = "IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = ?) " +
                "BEGIN CREATE DATABASE " + dbName + " END";

        jdbcTemplate.update(checkDbSql, dbName);
    }
}
