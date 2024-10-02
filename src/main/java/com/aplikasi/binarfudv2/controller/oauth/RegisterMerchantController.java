package com.aplikasi.binarfudv2.controller.oauth;

import com.aplikasi.binarfudv2.entity.oauth.User;
import com.aplikasi.binarfudv2.repo.oauth.UserRepo;
import com.aplikasi.binarfudv2.request.RegisterMerchantModel;
import com.aplikasi.binarfudv2.service.email.EmailSender;
import com.aplikasi.binarfudv2.service.oauth.MerchantOauthService;
import com.aplikasi.binarfudv2.util.Config;
import com.aplikasi.binarfudv2.util.EmailTemplate;
import com.aplikasi.binarfudv2.util.SimpleStringUtil;
import com.aplikasi.binarfudv2.util.TemplateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/merchant-register/")
public class RegisterMerchantController {
    @Autowired
    private UserRepo userRepo;

    Config config = new Config();

    @Autowired
    public MerchantOauthService serviceReq;

    @Autowired
    public EmailSender emailSender;
    @Autowired
    public EmailTemplate emailTemplate;

    @Value("${expired.token.password.minute:}")//FILE_SHOW_RUL
    private int expiredToken;

    @Autowired
    public TemplateResponse templateCRUD;

    @PostMapping("/register")
    public ResponseEntity<Map> saveRegisterManual(@Valid @RequestBody RegisterMerchantModel objModel) throws RuntimeException {
        Map map = new HashMap();

        User user = userRepo.checkExistingEmail(objModel.getUsername());
        if (null != user) {
            return new ResponseEntity<Map>(templateCRUD.Error("Username sudah ada"), HttpStatus.OK);
        }
        map = serviceReq.registerManual(objModel);

        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }


    @PostMapping("/register-google") // Step 1
    public ResponseEntity<Map> saveRegisterByGoogle(@Valid @RequestBody RegisterMerchantModel objModel) throws RuntimeException {
        Map map = new HashMap();

        User user = userRepo.checkExistingEmail(objModel.getUsername());
        if (null != user) {
            return new ResponseEntity<Map>(templateCRUD.Error("Username sudah ada"), HttpStatus.OK);

        }
        map = serviceReq.registerByGoogle(objModel);
        //gunanya send email otomatis
        Map mapRegister = sendEmailegister(objModel);
        return new ResponseEntity<Map>(mapRegister, HttpStatus.OK);
    }

    // Step 2: send OTP berupa URL guna update enable agar bisa login
    @PostMapping("/send-otp") //send OTP
    public Map sendEmailegister(
            @RequestBody RegisterMerchantModel user) {
        String message = "Thanks, please check your email for activation.";

        if (user.getUsername() == null) return templateCRUD.Success("No email provided");
        User found = userRepo.findOneByUsername(user.getUsername());
        if (found == null) return templateCRUD.Error("Email not found"); //throw new BadRequest("Email not found");

        String template = emailTemplate.getRegisterTemplate();
        if (StringUtils.isEmpty(found.getOtp())) {
            User search;
            String otp;
            do {
                otp = SimpleStringUtil.randomString(6, true);
                search = userRepo.findOneByOTP(otp);
            } while (search != null);
            Date dateNow = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateNow);
            calendar.add(Calendar.MINUTE, expiredToken);
            Date expirationDate = calendar.getTime();

            found.setOtp(otp);
            found.setOtpExpiredDate(expirationDate); //akan menjadi validasi
            template = template.replaceAll("\\{\\{USERNAME}}", (found.getFullname() == null ? found.getUsername() : found.getFullname()));
            template = template.replaceAll("\\{\\{VERIFY_TOKEN}}", otp);
            userRepo.save(found);
        } else {
            template = template.replaceAll("\\{\\{USERNAME}}", (found.getFullname() == null ? found.getUsername() : found.getFullname()));
            template = template.replaceAll("\\{\\{VERIFY_TOKEN}}", found.getOtp());
        }
        emailSender.sendAsync(found.getUsername(), "Register", template);
        return templateCRUD.Success(message);
    }

    //step ke 3
    @GetMapping("/register-confirm-otp/{token}")
    public ResponseEntity<Map> saveRegisterManual(@PathVariable(value = "token") String tokenOtp) throws RuntimeException {

        User user = userRepo.findOneByOTP(tokenOtp);
        if (null == user) {
            return new ResponseEntity<Map>(templateCRUD.templateError("OTP tidak ditemukan"), HttpStatus.OK);
        }
// validasi jika sebelumnya sudah melakukan aktifasi

        if (user.isEnabled()) {
            return new ResponseEntity<Map>(templateCRUD.templateSuccess("Akun Anda sudah aktif, Silahkan melakukan login"), HttpStatus.OK);
        }
        String today = config.convertDateToString(new Date());

        String dateToken = config.convertDateToString(user.getOtpExpiredDate());
        if (Long.parseLong(today) > Long.parseLong(dateToken)) {
            return new ResponseEntity<Map>(templateCRUD.templateError("Your token is expired. Please Get token again."), HttpStatus.OK);
        }
        //update user : ini yang berubah
        user.setEnabled(true);
        userRepo.save(user);

        return new ResponseEntity<Map>(templateCRUD.templateSuccess("Sukses, Silahkan Melakukan Login"), HttpStatus.OK);
    }
}