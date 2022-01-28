package com.springboot.shop.db.service.api;

import com.springboot.shop.domain.Customer;
import org.springframework.lang.Nullable;

import java.awt.*;
import java.util.List;

public interface CustomerService {

    List<Customer> getCustomers();

    @Nullable
    Customer get(int id);

    @Nullable
    Integer add(Customer customer); // returns generated id

}
