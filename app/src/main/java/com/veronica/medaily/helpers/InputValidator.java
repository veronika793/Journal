package com.veronica.medaily.helpers;

import android.text.TextUtils;
import android.webkit.URLUtil;

/**
 * Created by Veronica on 10/3/2016.
 */
public final class InputValidator {

    private InputValidator() {
        throw new AssertionError();
    }

    public static boolean isMinLenghRestricted(int minLenght,String value){
        return !value.trim().equals("") && value.trim().length() >= minLenght;
    }

    public static boolean isValidEmail(String email){
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidUri(String uri){
        return  URLUtil.isValidUrl(uri);
    }
    public static boolean validateLoginUserFields(String s ,String s2 ){
        return true;
    }

    public static boolean validateRegisterUserField(String s1,String s2,String s3, String s4){
        return true;
    }

    public static boolean isNullOrEmpty(String value){
        return TextUtils.isEmpty(value);
    }

    public static boolean isNumeric(String string){
        return TextUtils.isDigitsOnly(string);
    }


}
