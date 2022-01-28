package com.springboot.shop.db.repository;

import com.springboot.shop.db.mapper.MerchantRowMapper;

import com.springboot.shop.domain.Merchant;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Map;

@Component
public class MerchantRepository {
    private JdbcTemplate jdbcTemplate;
    private final MerchantRowMapper merchantRowMapper = new MerchantRowMapper();

    public MerchantRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    public Merchant get(int id){
        final String sql = "select * from merchant where merchant.id = "+id;
        try {
            return jdbcTemplate.queryForObject(sql,merchantRowMapper);
        } catch(EmptyStackException e){
        return null;

        }
    }

    public Integer add(Merchant merchant){
    final String sql = "insert into merchant(name, email, address) values (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            PreparedStatement ps = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1,merchant.getName());
            ps.setString(2,merchant.getEmail());
            ps.setString(3,merchant.getAddress());
            return ps;

            }
        },keyHolder);
        if(keyHolder!=null){
            return keyHolder.getKey().intValue();
        }else{
            return null;
        }


    }

    public List<Merchant> getAll(){
        final String sql = "select * from merchant";
        return jdbcTemplate.query(sql,merchantRowMapper);

    }

}
