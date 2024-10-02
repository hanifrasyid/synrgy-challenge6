package com.aplikasi.binarfudv2.service.oauth;

import com.aplikasi.binarfudv2.request.LoginModel;
import com.aplikasi.binarfudv2.request.RegisterMerchantModel;
import java.util.Map;

public interface MerchantOauthService {
    Map registerManual(RegisterMerchantModel objModel) ;

    Map registerByGoogle(RegisterMerchantModel objModel) ;

    public Map login(LoginModel objLogin);
}