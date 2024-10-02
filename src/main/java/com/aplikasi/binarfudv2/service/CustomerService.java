package com.aplikasi.binarfudv2.service;

import com.aplikasi.binarfudv2.entity.Customer;
import java.util.Map;

public interface CustomerService {
    Map addCustomer(Customer customer);
    Map updateCustomer(Customer customer);
    Map deleteCustomer(Customer customer);
    Map getByID(Long user);
}
