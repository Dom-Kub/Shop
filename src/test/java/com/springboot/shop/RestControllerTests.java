package com.springboot.shop;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.shop.db.service.api.request.UpdateProductRequest;
import com.springboot.shop.domain.Customer;
import com.springboot.shop.domain.Merchant;
import com.springboot.shop.domain.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.awt.*;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@AutoConfigureMockMvc
public class RestControllerTests {

   @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Merchant merchant;

    @Before
    public void createMerchant() throws Exception{
        if(merchant == null){
            merchant = new Merchant("Dominik","novy@mail.com","Neslusa");
            String id = mockMvc.perform(post("/merchant")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(merchant)))
                    .andExpect(status().isCreated())
                    .andReturn().getResponse().getContentAsString();

            merchant.setId(objectMapper.readValue(id,Integer.class));
        }

    }



    @Test
    public void product() throws Exception{
        Product product = new Product(merchant.getId(),"pes","Dobry pes",100.0,5);
        //Add product
        String id = mockMvc.perform(post("/product")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        product.setId(objectMapper.readValue(id,Integer.class));

        //Get product

        String productJson = mockMvc.perform(get("/product/"+product.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Product returnedProduct = objectMapper.readValue(productJson,Product.class);

        Assert.assertEquals(product, returnedProduct);

    //Get all

        String listJsnon = mockMvc.perform(get("/product")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<Product> products = objectMapper.readValue(listJsnon, new TypeReference<List<Product>>(){});

        assert products.size() == 1;
        Assert.assertEquals(product, products.get(0));



        // Update

        double updatePrice = product.getPrice()+1;
        int updatedavailable = product.getAvailable()+5;

        UpdateProductRequest updateProductRequest = new UpdateProductRequest(product.getName(),product.getDescription(),updatePrice,updatedavailable);
        mockMvc.perform(patch("/product/" +product.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updateProductRequest)))
        .andExpect(status().isOk());


        String returnedUpdatedProduct = mockMvc.perform(get("/product/"+product.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Product updatedProduct = objectMapper.readValue(returnedUpdatedProduct,Product.class);

        assert updatePrice == updatedProduct.getPrice();
        assert updatedavailable == updatedProduct.getAvailable();

        System.out.println(returnedUpdatedProduct);



        //Delete
        mockMvc.perform(delete("/product/" + product.getId())
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/product/"+product.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();


  }

    @Test
    public void customer() throws Exception {
        Customer customer = new Customer("Ferko", "Mrkvicka", "email", "address", 21, "xxx");

        // Add customer
        String id = mockMvc.perform(post("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        customer.setId(objectMapper.readValue(id,Integer.class));


        //Get customer
        String customerJson = mockMvc.perform(get("/customer/"+customer.getId())
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Customer returnedCustomer = objectMapper.readValue(customerJson,Customer.class);

        Assert.assertEquals(customer, returnedCustomer);


        // Get all customers
        String listJson = mockMvc.perform(get("/customer")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<Customer> customers = objectMapper.readValue(listJson, new TypeReference<List<Customer>>(){});

        assert customers.size() == 1;
        Assert.assertEquals(customer, customers.get(0));
    }

    @Test
    public void merchant() throws Exception{
       // Merchant is created

        // Add merchant
//        Merchant merchant = new Merchant("Dominik","novy@mail.com","Neslusa");
//        String id = mockMvc.perform(post("/merchant")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(merchant)))
//                .andExpect(status().isCreated())
//                .andReturn().getResponse().getContentAsString();
//
//        merchant.setId(objectMapper.readValue(id,Integer.class));


        //Get merchant
        String merchantJson = mockMvc.perform(get("/merchant/"+merchant.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Merchant returnedMerchant = objectMapper.readValue(merchantJson,Merchant.class);

        Assert.assertEquals(merchant, returnedMerchant);

        // Get all merchant
        String listJson = mockMvc.perform(get("/merchant")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<Merchant> merchants = objectMapper.readValue(listJson, new TypeReference<List<Merchant>>(){});

        assert merchants.size() == 1;
        Assert.assertEquals(merchant, merchants.get(0));

    }



}
