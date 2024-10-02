package com.aplikasi.binarfudv2.util;

import lombok.Data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

@Data
public class Config {
    String code = "status", message = "message";
    public String codeNotFound ="404";
    public String codeRequired ="403";
    public String isRequired =" is required";
    public String codeSuccess = "200";
    public String codeServer = "500";
    public String codeNull = "1";
    public String messageSuccess = "success";

    public String convertDateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String strDate = dateFormat.format(date);
        return strDate;
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }

    public static Integer ERROR_CODE_404 = 404;
    public static String NAME_REQUIRED = "name is required.";
    public static String ID_REQUIRED = "id is required.";
    public static String USERNAME_REQUIRED = "username is required.";
    public static String MERCHANT_NAME_REQUIRED = "merchant name is required.";
    public static String MERCHANT_REQUIRED = "merchant is required.";
    public static String MERCHANT_NOT_FOUND = "merchant not found.";
    public static String ORDER_REQUIRED = "order is required.";
    public static String ORDER_NOT_FOUND = "order not found.";
    public static String ORDER_DETAIL_REQUIRED = "order detail is required.";
    public static String ORDER_DETAIL_NOT_FOUND = "order detail not found.";
    public static String PRODUCT_REQUIRED = "product is required.";
    public static String PRODUCT_NOT_FOUND = "product not found.";
    public static String USER_REQUIRED = "user is required.";
    public static String USER_NOT_FOUND = "user not found.";
    public static String SUCCESS = "success.";
}