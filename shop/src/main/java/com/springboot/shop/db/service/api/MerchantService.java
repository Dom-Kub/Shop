package com.springboot.shop.db.service.api;

import com.springboot.shop.domain.Merchant;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

public interface MerchantService {

    List<Merchant> getMerchants();

    @NonNull
    Merchant get(int id);

    @NonNull
    Integer add(Merchant merchant); // return generated id
}
