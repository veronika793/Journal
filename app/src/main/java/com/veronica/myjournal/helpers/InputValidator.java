package com.veronica.myjournal.helpers;

import android.text.TextUtils;
import android.webkit.URLUtil;

/**
 * Created by Veronica on 10/3/2016.
 */
public  class InputValidator {

    public  boolean isMinLenghRestricted(int minLenght,String value){
        return !value.trim().equals("") && value.trim().length() >= minLenght;
    }

    public  boolean isValidEmail(String email){
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public  boolean isValidUri(String uri){
        return  URLUtil.isValidUrl(uri);
    }
    public  boolean validateLoginUserFields(String s ,String s2 ){
        return true;
    }

    public  boolean validateRegisterUserField(String s1,String s2,String s3, String s4){
        return true;
    }

    public  boolean isNullOrEmpty(String value){
        return TextUtils.isEmpty(value);
    }

    public boolean isNumeric(String string){
        return TextUtils.isDigitsOnly(string);
    }


}
