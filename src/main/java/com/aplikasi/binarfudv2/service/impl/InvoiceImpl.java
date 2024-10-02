package com.aplikasi.binarfudv2.service.impl;

import com.aplikasi.binarfudv2.entity.Customer;
import com.aplikasi.binarfudv2.entity.Merchant;
import com.aplikasi.binarfudv2.repo.CustomerRepo;
import com.aplikasi.binarfudv2.repo.MerchantRepo;
import com.aplikasi.binarfudv2.service.InvoiceService;
import com.aplikasi.binarfudv2.util.Config;
import com.aplikasi.binarfudv2.util.TemplateResponse;
import com.aplikasi.binarfudv2.util.jasper.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class InvoiceImpl implements InvoiceService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private MerchantRepo merchantRepo;

    @Autowired
    private TemplateResponse response;

    @Autowired
    private ReportService reportService;

    @Override
    public Map generateReport(Merchant merchant) {
        try {
            log.info("generate report");
            if (merchant.getId() == null) {
                return response.Error(Config.ID_REQUIRED);
            }
            if (merchant.getMerchant_name() == null) {
                return response.Error(Config.MERCHANT_NAME_REQUIRED);
            }
            Optional<Merchant> checkDataDBMerchant = merchantRepo.findById(merchant.getId());
            if (!checkDataDBMerchant.isPresent()) {
                return response.Error(Config.MERCHANT_NOT_FOUND);
            }
            Long id = merchant.getId();

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("IdMerchant", id);
            String pathUrl = ".\\src\\main\\resources\\Merchant.jrxml";
            String fileName = "Reporting Merchant "+merchant.getMerchant_name();
            return response.success(reportService.generate_pdf(parameters,pathUrl,fileName));
        } catch (Exception e) {
            log.error("generate report: " + e.getMessage());
            return response.Error("generate report =" + e.getMessage());
        }
    }

    @Override
    public Map generateInvoice(Customer customer) {
        try {
            log.info("generate invoice");
            if (customer.getId() == null) {
                return response.Error(Config.ID_REQUIRED);
            }
            if (customer.getName() == null) {
                return response.Error(Config.USERNAME_REQUIRED);
            }
            Optional<Customer> checkDataDBUser = customerRepo.findById(customer.getId());
            if (checkDataDBUser.isPresent()) {
                return response.Error(Config.USER_NOT_FOUND);
            }
            Long id = customer.getId();

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("IdUser", id);
            String pathUrl = ".\\src\\main\\resources\\User.jrxml";
            String fileName = "invoice pembelian "+ customer.getName();
            return response.success(reportService.generate_pdf(parameters,pathUrl,fileName));
        } catch (Exception e) {
            log.error("generate invoice: " + e.getMessage());
            return response.Error("generate invoice =" + e.getMessage());
        }
    }
}