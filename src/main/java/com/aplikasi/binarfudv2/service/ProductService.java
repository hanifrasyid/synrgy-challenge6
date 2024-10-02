package com.aplikasi.binarfudv2.service;

import com.aplikasi.binarfudv2.entity.Product;
import java.util.List;
import java.util.Map;

public interface ProductService {
    Map addProduct(Product product);
    Map updateProduct(Product product);
    Map deleteProduct(Product product);
    Map getByID(Long product);
    List<Product> getAvailableProduct();
}