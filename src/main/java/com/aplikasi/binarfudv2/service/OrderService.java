package com.aplikasi.binarfudv2.service;

import com.aplikasi.binarfudv2.entity.Order;
import java.util.List;
import java.util.Map;

public interface OrderService {
    Map createOrder(Order order);
    List<Order> getOrder();
    Map getByID(Long order);
    Map getByCustomerId(Long CustomerId);
}