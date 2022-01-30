package com.springboot.shop.db.service.api;

import com.springboot.shop.db.service.api.request.BuyProductRequest;
import com.springboot.shop.db.service.api.response.BuyProductResponse;

public interface ShoppingService {
    BuyProductResponse buyProduct(BuyProductRequest request);
}
