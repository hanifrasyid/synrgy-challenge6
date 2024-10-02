package com.aplikasi.binarfudv2.request;

import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class RegisterMerchantModel {
    @NotEmpty(message = "username is required.")
    private String username;

    @NotEmpty(message = "password is required.")
    private String password;

    @NotEmpty(message = "merchant name is required.")
    private String merchant_name;

    private String merchant_location;
}
