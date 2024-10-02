package com.aplikasi.binarfudv2.util.jasper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Main {
    @Autowired
    public  ReportService reportService;

    @Test
    public  void testPembelianUser() throws SQLException {
        Map<String, Object> parameters33 = new HashMap<>();
        parameters33.put("IdUser", 1L);
        String pathUrl = ".\\src\\main\\resources\\User.jrxml";
        String fileName = "invoice pembelian";
        reportService.generate_pdf(parameters33,pathUrl,fileName);
        reportService.generateHtml(parameters33,pathUrl,fileName);
        reportService.generate_excel(parameters33,pathUrl,fileName);
        reportService.generateCSV(parameters33,pathUrl,fileName);
        reportService.generateDocx(parameters33,pathUrl,fileName);
    }

    @Test
    public  void testReportingMerchant() throws SQLException {
        Map<String, Object> parameters33 = new HashMap<>();
        parameters33.put("IdMerchant", 4L);
        String pathUrl = ".\\src\\main\\resources\\Merchant.jrxml";
        String fileName = "Reporting Merchant saya";
        reportService.generate_pdf(parameters33,pathUrl,fileName);
        reportService.generateHtml(parameters33,pathUrl,fileName);
        reportService.generate_excel(parameters33,pathUrl,fileName);
        reportService.generateCSV(parameters33,pathUrl,fileName);
        reportService.generateDocx(parameters33,pathUrl,fileName);
    }
}