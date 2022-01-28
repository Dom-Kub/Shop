package com.springboot.shop.db.service.api;


import com.springboot.shop.db.service.api.request.UpdateProductRequest;
import com.springboot.shop.domain.Product;
import org.springframework.lang.Nullable;

import java.util.List;

public interface ProductService {
    List<Product> getProducts();

    @Nullable
    Product get(int id);

    @Nullable
    Integer add(Product product); // returns generated id

    void delete(int id);

    void update(int id, UpdateProductRequest request);

    void updateAvailableInternal(int id, int newAvailable);
}

