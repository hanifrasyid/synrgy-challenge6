package com.aplikasi.binarfudv2.controller;

import com.aplikasi.binarfudv2.entity.Product;
import com.aplikasi.binarfudv2.repo.ProductRepo;
import com.aplikasi.binarfudv2.service.ProductService;
import com.aplikasi.binarfudv2.util.TemplateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/productAuth")
public class ProductAuthController {

    @Autowired
    private ProductService productService;

    @Autowired
    public ProductRepo productRepo;

    @Autowired
    public TemplateResponse response;

    @PostMapping(value = {"/add","/add/"})
    public ResponseEntity<Map> addProduct(@RequestBody Product product){
        try {
            return new ResponseEntity<Map>(productService.addProduct(product), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @PutMapping(value = {"/update","/update/"})
    public ResponseEntity<Map> updateProduct(@RequestBody Product product) {
        try {
            return new ResponseEntity<Map>(productService.updateProduct(product), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @DeleteMapping(value = {"/delete","/delete/"})
    public ResponseEntity<Map> deleteProduct(@RequestBody Product request) {
        try {
            return new ResponseEntity<Map>(productService.deleteProduct(request), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(response.Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }
}