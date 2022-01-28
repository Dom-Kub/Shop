package com.springboot.shop.controller;

import com.springboot.shop.db.service.api.MerchantService;
import com.springboot.shop.domain.Merchant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("merchant")
public class MerchantController {

    private MerchantService merchantService;

    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @PostMapping
    public ResponseEntity add(@RequestBody Merchant merchant){
        Integer id = merchantService.add(merchant);
        if(id!=null){
            return new ResponseEntity(id, HttpStatus.CREATED);
        }
        return new ResponseEntity(id, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("{id}")
    public ResponseEntity get(@PathVariable("id") int id){
        Merchant merchant = merchantService.get(id);
        if(merchant == null){
            return new ResponseEntity(merchant, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(merchant, HttpStatus.OK);


    }

    @GetMapping
    public ResponseEntity getAll(){
        List<Merchant> merchants = merchantService.getMerchants();
        return new ResponseEntity<>(merchants, HttpStatus.OK);

    }


}
