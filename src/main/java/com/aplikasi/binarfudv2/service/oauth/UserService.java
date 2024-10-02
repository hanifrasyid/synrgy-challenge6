package com.aplikasi.binarfudv2.service.oauth;

import com.aplikasi.binarfudv2.request.LoginModel;
import com.aplikasi.binarfudv2.request.RegisterUserModel;
import java.util.Map;

public interface UserService {
    Map registerManual(RegisterUserModel objModel) ;

    Map registerByGoogle(RegisterUserModel objModel) ;

    public Map login(LoginModel objLogin);
}