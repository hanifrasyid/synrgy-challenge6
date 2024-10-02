package com.aplikasi.binarfudv2.util;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class TemplateResponse {

    public Map success(Object obj){
        Map map = new HashMap();
        map.put("data", obj);
        map.put("status", 200);
        map.put("message", "success");
        return map;
    }

    public Map Success(Object objek){
        Map map = new HashMap();
        map.put("message", "Success");
        map.put("status", 200);
        map.put("data", objek);
        return map;
    }

    public Map error(Object obj, Object code){
        Map map = new HashMap();
        map.put("status", code);
        map.put("message", obj);
        return map;
    }

    public boolean nameNotSimbol(String nama){
        String nameRegex = "^[a-zA-Z\\s]*$";
        Pattern pat = Pattern.compile(nameRegex);
        return pat.matcher(nama).matches();
    }

    public Map Error(Object objek){
        Map map = new HashMap();
        map.put("message", objek);
        map.put("status", 404);
        return map;
    }

    public boolean checkNull(Object obj){
        if(obj == null){ return true; }
        return  false;
    }

    public Double convDoubleIfNull(Double obj){
        if(obj == null){ return 0.0; }
        return  obj;
    }
    public Integer convIntegerIfNull(Integer obj){
        if(obj == null){ return 0; }
        return  obj;
    }

    public String convStringIfNull(String obj){
        if(obj == null){ return ""; }
        return  obj;
    }
    public Boolean convBooleanIfNull(Boolean obj){
        if(obj == null){ return false; }
        return  obj;
    }

    public Boolean isRequired(Object obj){
        if(obj == null){ return  true; }
        return false;
    }


    public Map isRequired(String obj)  {
        Map map = new HashMap();
        map.put("message", obj);
        map.put("status", 404);
        return map;
    }

    public Map templateSuccess(Object objek){
        Map map = new HashMap();
        map.put("data", objek);
        map.put("message", "success");
        map.put("status", 200);
        return map;
    }

    public Map templateSuccess(Object objek, Object objek2){
        Map map = new HashMap();
        map.put("data 1", objek);
        map.put("data 2", objek2);
        map.put("message", "success");
        map.put("status", 200);
        return map;
    }

    public Map templateError(Object objek){
        Map map = new HashMap();
        map.put("message", objek);
        map.put("status", 404);
        return map;
    }
    public Map notFound(Object objek){
        Map map = new HashMap();
        map.put("message", objek);
        map.put("status", 404);
        return map;
    }
    public boolean isValidEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }
}