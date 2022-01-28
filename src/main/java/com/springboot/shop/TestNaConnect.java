package com.springboot.shop;

import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Service;

@Service
public class TestNaConnect {
    private JdbcTemplate jdbcTemplate;

    public TestNaConnect(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcTemplate.execute("select * from customer");
    }

}
