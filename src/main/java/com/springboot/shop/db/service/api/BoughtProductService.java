package com.springboot.shop.db.service.api;

import com.springboot.shop.domain.BoughtProduct;

import java.util.List;

public interface BoughtProductService {

    void add(BoughtProduct boughtProduct);

    List<BoughtProduct> getAllByCustomerId(int customerId);


}
