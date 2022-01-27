package com.springboot.shop;


import com.springboot.shop.db.service.api.CustomerService;
import com.springboot.shop.db.service.api.MerchantService;
import com.springboot.shop.db.service.api.ProductService;
import com.springboot.shop.db.service.api.request.UpdateProductReguest;
import com.springboot.shop.domain.Customer;
import com.springboot.shop.domain.Merchant;
import com.springboot.shop.domain.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DBserviceTests {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private ProductService productService;

    private Merchant merchant;

    @Before
    public void createMerchant(){
        if(merchant == null){
            merchant = new Merchant("Tibor","gmail.com","Posledná ulica");
            Integer id = merchantService.add(merchant);
            assert id != 0;
            merchant.setId(id);
        }

    }

    @Test
    public void customer(){
        Customer customer = new Customer("Jaro","Peter","email.com","Nova",20,"456756456");
        Integer id = customerService.add(customer);
        assert id != null;
        customer.setId(id);

        Customer fromDB = customerService.get(id);
        Assert.assertEquals(customer,fromDB);

        List<Customer> customers = customerService.getCustomers();
        Assert.assertEquals(1,customers.size());
        Assert.assertEquals(customer, customers.get(0));

    }

    @Test
    public void merchant(){
        // už je vytvoreny
//        merchant = new Merchant("Tibor","gmail.com","Posledná ulica");
//        Integer id = merchantService.add(merchant);
//        assert id != 0;
//        merchant.setId(id);

        Merchant fromDB = merchantService.get(merchant.getId());
        Assert.assertEquals(merchant,fromDB);

        List<Merchant> merchants = merchantService.getMerchants();
        Assert.assertEquals(1,merchants.size());
        Assert.assertEquals(merchant,merchants.get(0));



    }
    @Test
    public void product(){
        Product product = new Product(merchant.getId(),"monitor","herny",2000.0,1);
        Integer id = productService.add(product);
        assert id != null;
        product.setId(id);

        Product fromDb = productService.get(id);
        Assert.assertEquals(product,fromDb);

        List<Product> products = productService.getProducts();
        Assert.assertEquals(1,products.size());
        Assert.assertEquals(product,products.get(0));

        product.setAvailable(10);
        UpdateProductReguest productReguest = new UpdateProductReguest(product.getName(),product.getDescription(),product.getPrice(),product.getAvailable());

        productService.update(id,productReguest);

        Product fromDbAfterUpdate = productService.get(id);
        Assert.assertEquals(product,fromDbAfterUpdate);
        Assert.assertNotEquals(fromDb,fromDbAfterUpdate);

        productService.delete(id);
        Assert.assertEquals(0,productService.getProducts().size());

    }
}
