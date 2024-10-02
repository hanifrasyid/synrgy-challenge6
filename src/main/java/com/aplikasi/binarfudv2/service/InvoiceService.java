package com.aplikasi.binarfudv2.service;

import com.aplikasi.binarfudv2.entity.Customer;
import com.aplikasi.binarfudv2.entity.Merchant;

import java.util.Map;

public interface InvoiceService {
    Map generateReport(Merchant merchant);
    Map generateInvoice(Customer customer);
}