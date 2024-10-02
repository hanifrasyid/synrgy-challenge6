package com.aplikasi.binarfudv2.service.impl;

import com.aplikasi.binarfudv2.entity.Customer;
import com.aplikasi.binarfudv2.entity.Order;
import com.aplikasi.binarfudv2.repo.CustomerRepo;
import com.aplikasi.binarfudv2.repo.OrderRepo;
import com.aplikasi.binarfudv2.service.OrderService;
import com.aplikasi.binarfudv2.util.Config;
import com.aplikasi.binarfudv2.util.TemplateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class OrderImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private TemplateResponse response;

    @Override
    public Map createOrder(Order order) {
        try {
            log.info("create order");
            return response.success(orderRepo.save(order));
        } catch (Exception e){
            log.error("create order error : " + e.getMessage());
            return response.Error("create order = " + e.getMessage());
        }
    }

    @Override
    public List<Order> getOrder() {
        log.info("get all order");
        return orderRepo.findAll();
    }

    @Override
    public Map getByID(Long order) {
        Optional<Order> getBaseOptional = orderRepo.findById(order);
        if(!getBaseOptional.isPresent()){
            return response.notFound(getBaseOptional);
        }
        return response.templateSuccess(getBaseOptional);
    }

    @Override
    public Map getByCustomerId(Long CustomerId) {
        try {
            log.info("get order by user");
            if (CustomerId == null) {
                return response.Error(Config.ID_REQUIRED);
            }
            Optional<Customer> checkDataDBCustomer = customerRepo.findById(CustomerId);
            if (!checkDataDBCustomer.isPresent()) {
                return response.Error(Config.USER_NOT_FOUND);
            }
            checkDataDBCustomer.get().setId(CustomerId);
            Optional<Order> getBaseOptional = orderRepo.findByCustomerId(checkDataDBCustomer.get());
            if(!getBaseOptional.isPresent()){
                return response.notFound(getBaseOptional);
            }
            return response.templateSuccess(getBaseOptional);
        } catch (Exception e){
            log.error("get order by customer error: " + e.getMessage());
            return response.Error("get order by customer = " + e.getMessage());
        }
    }
}