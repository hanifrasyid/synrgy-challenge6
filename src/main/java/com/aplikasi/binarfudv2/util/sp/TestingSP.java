package com.aplikasi.binarfudv2.util.sp;

import com.aplikasi.binarfudv2.repo.MerchantRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import javax.sql.DataSource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class TestingSP {
    @Autowired
    private DataSource dataSource;

    @Autowired
    public MerchantRepo merchantRepo;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public MerchantQuerySP merchantQuerySP;
    @Before
    public void init() {
        try {
            jdbcTemplate.execute(merchantQuerySP.getData);
            jdbcTemplate.execute(merchantQuerySP.getDataMerchantLikeName);
            jdbcTemplate.execute(merchantQuerySP.insertMerchant);
            jdbcTemplate.execute(merchantQuerySP.updateMerchant);
            jdbcTemplate.execute(merchantQuerySP.deletedMerchant);
        } finally {
//            session.close();
        }
    }

    @Test
    public void listSP(){
        List<Object> obj =  merchantRepo.getListSP();
        System.out.println(obj);
    }

    @Test
    public void getIdSp(){
        Object obj =  merchantRepo.getemerchantbyid(6L);
        System.out.println(obj);
    }

    @Test
    public void saveSp(){
        Long resid = null;
        merchantRepo.saveMerchantSP(resid, "spring boot");
    }

    @Test
    public void updateSP(){
        merchantRepo.updateMerchantSP(6L, "spring boot1");
    }
    @Test
    public void deletedSp(){
        merchantRepo.deletedMerchantSP(8L);
    }

}