package com.aplikasi.binarfudv2.service.impl;

import com.aplikasi.binarfudv2.entity.Customer;
import com.aplikasi.binarfudv2.repo.CustomerRepo;
import com.aplikasi.binarfudv2.service.CustomerService;
import com.aplikasi.binarfudv2.util.Config;
import com.aplikasi.binarfudv2.util.TemplateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class CustomerImpl implements CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private TemplateResponse response;

    @Override
    public Map addCustomer(Customer customer) {
        try {
            log.info("add User");
            return response.success(customerRepo.save(customer));
        }catch (Exception e){
            log.error("add user error: " + e.getMessage());
            return response.Error("add user =" + e.getMessage());
        }
    }

    @Override
    public Map updateCustomer(Customer customer) {
        try {
            log.info("Update User");
            if (customer.getId() == null) {
                return response.Error(Config.ID_REQUIRED);
            }
            Optional<Customer> checkDataDBUser = customerRepo.findById(customer.getId());
            if (!checkDataDBUser.isPresent()) {
                return response.Error(Config.USER_NOT_FOUND);
            }
            checkDataDBUser.get().setName(customer.getName());
            checkDataDBUser.get().setAdress(customer.getAdress());
            checkDataDBUser.get().setGender(customer.getGender());
            checkDataDBUser.get().setUpdated_date(new Date());

            return response.success(customerRepo.save(checkDataDBUser.get()));
        } catch (Exception e){
            log.error("Update user error: " + e.getMessage());
            return response.Error("Update user = " + e.getMessage());
        }
    }

    @Override
    public Map deleteCustomer(Customer customer) {
        try {
            log.info("Delete user");
            if (customer.getId() == null) {
                return response.Error(Config.ID_REQUIRED);
            }
            Optional<Customer> checkDataDBUser = customerRepo.findById(customer.getId());
            if (!checkDataDBUser.isPresent()) {
                return response.Error(Config.USER_NOT_FOUND);
            }

            checkDataDBUser.get().setDeleted_date(new Date());
            customerRepo.save(checkDataDBUser.get());
            return response.success(Config.SUCCESS);
        } catch (Exception e){
            log.error("Delete User error: "+e.getMessage());
            return response.Error("Delete User =" + e.getMessage());
        }
    }

    @Override
    public Map getByID(Long user) {
        Optional<Customer> getBaseOptional = customerRepo.findById(user);
        if(!getBaseOptional.isPresent()){
            return response.notFound(getBaseOptional);
        }
        return response.templateSuccess(getBaseOptional);
    }
}