package com.aplikasi.binarfudv2.controller;

import com.aplikasi.binarfudv2.entity.Merchant;
import com.aplikasi.binarfudv2.repo.MerchantRepo;
import com.aplikasi.binarfudv2.service.MerchantService;
import com.aplikasi.binarfudv2.util.SimpleStringUtil;
import com.aplikasi.binarfudv2.util.TemplateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/merchantAuth")
public class MerchantAuthController {

    @Autowired
    private MerchantService merchantService;
    SimpleStringUtil simpleStringUtil = new SimpleStringUtil();

    @Autowired
    public MerchantRepo merchantRepo;

    @Autowired
    public TemplateResponse response;

    @PutMapping(value ={"/updateStatus", "/updateStatus/"})
    public ResponseEntity<Map> updateMerchantStatus(@RequestBody Merchant request) {
        try {
            return new ResponseEntity<Map>(merchantService.updateMerchantStatus(request), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @PutMapping(value={"/update", "/update/"})
    public ResponseEntity<Map> update(@RequestBody Merchant request) {
        try {
            return new ResponseEntity<Map>(merchantService.update(request), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }
}