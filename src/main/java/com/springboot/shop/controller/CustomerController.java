package com.springboot.shop.controller;

import com.springboot.shop.db.service.api.CustomerService;
import com.springboot.shop.domain.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customer")
public class CustomerController {

   private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity add(@RequestBody  Customer customer){
        Integer id = customerService.add(customer);
        if(id!=null){
            return new ResponseEntity<>(id,HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("{id}")
    public ResponseEntity get(@PathVariable("id") int id){
        Customer customer = customerService.get(id);
        if(customer == null){
            return new ResponseEntity<>(customer, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity getAll(){
         List<Customer> customerList = customerService.getCustomers();
         return new ResponseEntity<>(customerList, HttpStatus.OK);
    }




}
