package com.aplikasi.binarfudv2.service.impl;

import com.aplikasi.binarfudv2.entity.Merchant;
import com.aplikasi.binarfudv2.repo.MerchantRepo;
import com.aplikasi.binarfudv2.service.MerchantService;
import com.aplikasi.binarfudv2.util.Config;
import com.aplikasi.binarfudv2.util.TemplateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@Slf4j
public class MerchantImpl implements MerchantService {

    @Autowired
    private MerchantRepo merchantRepo;

    @Autowired
    private TemplateResponse response;

    @Override
    public Map addMerchant(Merchant merchant) {
        try {
            log.info("add merchant");
            return response.success(merchantRepo.save(merchant));
        }catch (Exception e){
            log.error("add merchant error: " + e.getMessage());
            return response.Error("add merchant = " + e.getMessage());
        }
    }

    @Override
    public Map updateMerchantStatus(Merchant merchant) {
        try {
            log.info("Update merchant status");
            if (merchant.getId() == null) {
                return response.Error(Config.ID_REQUIRED);
            }
            Optional<Merchant> checkDataDBMerchant = merchantRepo.findById(merchant.getId());
            if (!checkDataDBMerchant.isPresent()) {
                return response.Error(Config.MERCHANT_NOT_FOUND);
            }
            checkDataDBMerchant.get().setOpen(merchant.getOpen());
            checkDataDBMerchant.get().setUpdated_date(new Date());

            return response.success(merchantRepo.save(checkDataDBMerchant.get()));
        } catch (Exception e){
            log.error("Update merchant status error: "+e.getMessage());
            return response.Error("Update merchant status = " + e.getMessage());
        }
    }

    @Override
    public Map update(Merchant merchant) {
        try {
            log.info("Update merchant");
            if (merchant.getId() == null) {
                return response.Error(Config.ID_REQUIRED);
            }
            Optional<Merchant> checkDataDBMerchant = merchantRepo.findById(merchant.getId());
            if (!checkDataDBMerchant.isPresent()) {
                return response.Error(Config.MERCHANT_NOT_FOUND);
            }

            checkDataDBMerchant.get().setMerchant_name(merchant.getMerchant_name());
            checkDataDBMerchant.get().setMerchant_location(merchant.getMerchant_location());
            checkDataDBMerchant.get().setOpen(merchant.getOpen());
            checkDataDBMerchant.get().setUpdated_date(new Date());

            return response.success(merchantRepo.save(checkDataDBMerchant.get()));
        } catch (Exception e){
            log.error("Update merchant error: " + e.getMessage());
            return response.Error("Update merchant = " + e.getMessage());
        }
    }

    @Override
    public Map delete(Merchant merchant) {
        try {
            log.info("Delete merchant");
            if (merchant.getId() == null) {
                return response.Error(Config.ID_REQUIRED);
            }
            Optional<Merchant> checkDataDBMerchant = merchantRepo.findById(merchant.getId());
            if (!checkDataDBMerchant.isPresent()) {
                return response.Error(Config.MERCHANT_NOT_FOUND);
            }

            checkDataDBMerchant.get().setDeleted_date(new Date());
            merchantRepo.save(checkDataDBMerchant.get());
            return response.success(Config.SUCCESS);
        }catch (Exception e){
            log.error("Delete merchant error: " + e.getMessage());
            return response.Error("Delete merchant = " + e.getMessage());
        }
    }

    @Override
    public Map getByID(Long merchant) {
        Optional<Merchant> getBaseOptional = merchantRepo.findById(merchant);
        if(!getBaseOptional.isPresent()){
            return response.notFound(getBaseOptional);
        }
        return response.templateSuccess(getBaseOptional);
    }

    @Override
    public List<Merchant> getOpenMerchant() {
        return merchantRepo.findByOpenIsTrue();
    }
}