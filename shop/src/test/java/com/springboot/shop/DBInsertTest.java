package com.springboot.shop;


import com.springboot.shop.domain.Customer;
import com.springboot.shop.domain.Merchant;
import com.springboot.shop.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

@SpringBootTest
public class DBInsertTest {


    private final String insertCustomer = "INSERT INTO customer(name, surname, email, address, age, phone_number) values (?,?,?,?,?,?)";
    private final String insertMerchant = "INSERT INTO merchant(name, email, address) values (?,?,?)";
    private final String insertProduct = "INSERT INTO product(merchant_id, name, description, price, created_at, available) values (?,?,?,?,?,?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void createdProduct(){
        Product product = new Product();
        product.setMerchant_id(1);
        product.setName("klavesnica");
        product.setDescription("Herna");
        product.setPrice(1000.0);
        product.setCreatedAt(Timestamp.from(Instant.now()));
        product.setAvailable(5);

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(insertProduct);
                ps.setInt(1,product.getMerchant_id());
                ps.setString(2,product.getName());
                ps.setString(3,product.getDescription());
                ps.setDouble(4,product.getPrice());
                ps.setTimestamp(5,product.getCreatedAt());
                ps.setInt(6,product.getAvailable());
                return ps;
            }
        });
    }

     @Test
    public void createdCustomer(){
         Customer customer = new Customer();
         customer.setName("Peter");
         customer.setSurname("Pan");
         customer.setEmail("peter.pan@mail");
         customer.setAddress("PetraPana80");
         customer.setAge(17);
         customer.setPhoneNumber("5456874");

         jdbcTemplate.update(new PreparedStatementCreator() {
             @Override
             public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                 PreparedStatement ps = con.prepareStatement(insertCustomer);
                 ps.setString(1,customer.getName());
                 ps.setString(2,customer.getSurname());
                 ps.setString(3,customer.getEmail());
                 ps.setString(4,customer.getAddress());
                 ps.setInt(5,customer.getAge());
                 ps.setString(6,customer.getPhoneNumber());
                 return ps;
             }
         });

     }
    @Test
     public void createdMerchant(){
         Merchant merchant = new Merchant();
         merchant.setName("Ondrej");
         merchant.setEmail("ondrej.prvy@mail.com");
         merchant.setAddress("Prvá ulica");

         jdbcTemplate.update(new PreparedStatementCreator() {
             @Override
             public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                 PreparedStatement ps = con.prepareStatement(insertMerchant);
                 ps.setString(1, merchant.getName());
                 ps.setString(2,merchant.getEmail());
                 ps.setString(3,merchant.getAddress());
                 return ps;

             }
         });


     }


}
