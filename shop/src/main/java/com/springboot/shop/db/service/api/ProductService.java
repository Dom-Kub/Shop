package com.springboot.shop.db.service.api;

import com.springboot.shop.db.service.api.request.UpdateProductReguest;
import com.springboot.shop.domain.Product;
import org.springframework.lang.Nullable;

import java.util.List;

public interface ProductService {
    List<Product> getProducts();

    @Nullable
    Product get(int id);

    @Nullable
    Integer add(Product product); //return generated id

    void delete(int id);

    void update(int id, UpdateProductReguest reguest);

}
