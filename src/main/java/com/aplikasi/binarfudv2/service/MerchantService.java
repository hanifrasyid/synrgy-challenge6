package com.aplikasi.binarfudv2.service;

import com.aplikasi.binarfudv2.entity.Merchant;
import java.util.List;
import java.util.Map;

public interface MerchantService {
    Map addMerchant(Merchant merchan);
    Map update(Merchant merchant);
    Map updateMerchantStatus(Merchant merchant);
    Map delete(Merchant merchant);
    Map getByID(Long merchant);
    List<Merchant> getOpenMerchant();
}