package com.aplikasi.binarfudv2.request;

import lombok.Data;

@Data
public class ResetPasswordModel {
    public String email;
    public String otp;
    public String newPassword;
}