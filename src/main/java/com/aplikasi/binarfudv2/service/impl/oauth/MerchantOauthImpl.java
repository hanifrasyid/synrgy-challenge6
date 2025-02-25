package com.aplikasi.binarfudv2.service.impl.oauth;

import com.aplikasi.binarfudv2.entity.Merchant;
import com.aplikasi.binarfudv2.entity.oauth.Role;
import com.aplikasi.binarfudv2.entity.oauth.User;
import com.aplikasi.binarfudv2.repo.MerchantRepo;
import com.aplikasi.binarfudv2.repo.oauth.RoleRepo;
import com.aplikasi.binarfudv2.repo.oauth.UserRepo;
import com.aplikasi.binarfudv2.request.LoginModel;
import com.aplikasi.binarfudv2.request.RegisterMerchantModel;
import com.aplikasi.binarfudv2.service.oauth.MerchantOauthService;
import com.aplikasi.binarfudv2.util.Config;
import com.aplikasi.binarfudv2.util.TemplateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MerchantOauthImpl implements MerchantOauthService {

    Config config = new Config();

    private static final Logger logger = LoggerFactory.getLogger(MerchantOauthImpl.class);
    @Autowired
    RoleRepo roleRepo;

    @Autowired
    UserRepo repoUser;

    @Autowired
    MerchantRepo repoMerchant;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    public TemplateResponse templateResponse;

    @Value("${BASEURL}")
    private String baseUrl;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private UserRepo userRepo;

    @Override
    public Map registerManual(RegisterMerchantModel objModel) {
        Map map = new HashMap();
        try {
            String[] roleNames = {"ROLE_MERCHANT", "ROLE_READ", "ROLE_WRITE"}; // admin
            User user = new User();

            user.setUsername(objModel.getUsername().toLowerCase());
            user.setFullname(objModel.getMerchant_name());

            Merchant merchant = new Merchant();
            merchant.setMerchant_name(objModel.getMerchant_name());
            merchant.setMerchant_location(objModel.getMerchant_location());
            //step 1 :
//            user.setEnabled(false); // matikan user

            String password = encoder.encode(objModel.getPassword().replaceAll("\\s+", ""));
            List<Role> r = roleRepo.findByNameIn(roleNames);

            user.setRoles(r);
            user.setPassword(password);

            User objUser = repoUser.save(user);
            Merchant objMerchant = repoMerchant.save(merchant);

            return templateResponse.templateSuccess(objUser, objMerchant);

        } catch (Exception e) {
            logger.error("Error registerManual = ", e);
            return templateResponse.templateError("error:"+e);
        }

    }
    @Override
    public Map registerByGoogle(RegisterMerchantModel objModel) {
        Map map = new HashMap();
        try {
            String[] roleNames = {"ROLE_MERCHANT", "ROLE_READ", "ROLE_WRITE"}; // ROLE DEFAULE
            User user = new User();
            user.setUsername(objModel.getUsername().toLowerCase());
            user.setFullname(objModel.getMerchant_name());

            Merchant merchant = new Merchant();
            merchant.setMerchant_name(objModel.getMerchant_name());
            merchant.setMerchant_location(objModel.getMerchant_location());

            //step 1 :
            user.setEnabled(false); // matikan user : tujuan kita inactivate
            String password = encoder.encode(objModel.getPassword().replaceAll("\\s+", ""));
            List<Role> r = roleRepo.findByNameIn(roleNames);
            user.setRoles(r);
            user.setPassword(password);
            User obj = repoUser.save(user);
            Merchant objMerchant = repoMerchant.save(merchant);
            return templateResponse.templateSuccess(obj, objMerchant);

        } catch (Exception e) {
            logger.error("Error registerManual=", e);
            return templateResponse.Error("error: " + e);
        }
    }

    @Override
    public Map login(LoginModel loginModel) {
        /**
         * bussines logic for login here
         * **/
        try {
            Map<String, Object> map = new HashMap<>();

            User checkUser = repoUser.findOneByUsername(loginModel.getUsername());

            if ((checkUser != null) && (encoder.matches(loginModel.getPassword(), checkUser.getPassword()))) {
                if (!checkUser.isEnabled()) {
                    map.put("is_enabled", checkUser.isEnabled());
                    return templateResponse.templateError(map);
                }
            }
            if (checkUser == null) {
                return templateResponse.notFound("user not found");
            }
            if (!(encoder.matches(loginModel.getPassword(), checkUser.getPassword()))) {
                return templateResponse.templateError("wrong password");
            }
            String url = baseUrl + "/oauth/token?username=" + loginModel.getUsername() +
                    "&password=" + loginModel.getPassword() +
                    "&grant_type=password" +
                    "&client_id=my-client-web" +
                    "&client_secret=password";
            ResponseEntity<Map> response = restTemplateBuilder.build().exchange(url, HttpMethod.POST, null, new
                    ParameterizedTypeReference<Map>() {
                    });

            if (response.getStatusCode() == HttpStatus.OK) {
                User user = userRepo.findOneByUsername(loginModel.getUsername());
                List<String> roles = new ArrayList<>();

                for (Role role : user.getRoles()) {
                    roles.add(role.getName());
                }
                //save token
//                checkUser.setAccessToken(response.getBody().get("access_token").toString());
//                checkUser.setRefreshToken(response.getBody().get("refresh_token").toString());
//                userRepository.save(checkUser);

                map.put("access_token", response.getBody().get("access_token"));
                map.put("token_type", response.getBody().get("token_type"));
                map.put("refresh_token", response.getBody().get("refresh_token"));
                map.put("expires_in", response.getBody().get("expires_in"));
                map.put("scope", response.getBody().get("scope"));
                map.put("jti", response.getBody().get("jti"));

                return map;
            } else {
                return templateResponse.notFound("merchant not found");
            }
        } catch (HttpStatusCodeException e) {
            e.printStackTrace();
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return templateResponse.templateError("invalid login: " + e);
            }
            return templateResponse.templateError(e);
        } catch (Exception e) {
            e.printStackTrace();

            return templateResponse.templateError(e);
        }
    }
}