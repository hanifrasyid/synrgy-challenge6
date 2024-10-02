package com.aplikasi.binarfudv2.controller;

import com.aplikasi.binarfudv2.entity.Customer;
import com.aplikasi.binarfudv2.repo.CustomerRepo;
import com.aplikasi.binarfudv2.service.CustomerService;
import com.aplikasi.binarfudv2.util.SimpleStringUtil;
import com.aplikasi.binarfudv2.util.TemplateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/customerAuth")
public class CustomerAuthController {

    @Autowired
    private CustomerService customerService;

    SimpleStringUtil simpleStringUtil = new SimpleStringUtil();

    @Autowired
    public CustomerRepo customerRepo;

    @Autowired
    public TemplateResponse response;

    @PutMapping(value={"/update", "/update/"})
    public ResponseEntity<Map> updateCustomer(@RequestBody Customer customer) {
        try {
            return new ResponseEntity<Map>(customerService.updateCustomer(customer), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @DeleteMapping(value={"/delete", "/delete/"})
    public ResponseEntity<Map> deleteCustomer(@RequestBody Customer customer) {
        try {
            return new ResponseEntity<Map>(customerService.deleteCustomer(customer), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }
}