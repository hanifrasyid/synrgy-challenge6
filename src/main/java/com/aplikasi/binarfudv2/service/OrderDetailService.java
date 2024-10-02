package com.aplikasi.binarfudv2.service;

import com.aplikasi.binarfudv2.entity.OrderDetail;
import java.util.Map;

public interface OrderDetailService {
    Map addOrderDetail(OrderDetail orderDetail);
    Map updateOrderDetail(OrderDetail orderDetail);
    Map deleteOrderDetail(OrderDetail orderDetail);
    Map getByID(Long orderDetail);
}