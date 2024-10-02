package com.aplikasi.binarfudv2.util.sp;

import com.aplikasi.binarfudv2.repo.MerchantRepo;
import com.aplikasi.binarfudv2.service.MerchantService;
import com.aplikasi.binarfudv2.util.SimpleStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/merchant/sp")
public class MerchantControllerSP {

    @Autowired
    public MerchantService merchantService;

    SimpleStringUtil simpleStringUtils = new SimpleStringUtil();

    @Autowired
    private DataSource dataSource;

    @Autowired
    public MerchantRepo merchantRepo;

    @GetMapping(value = {"", "/"})
    public ResponseEntity<Map> getById() {
        Map map = new HashMap();
        map.put("list",merchantRepo.getListSP());
        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }
}