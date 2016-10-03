package com.veronica.myjournal.helpers;

import android.webkit.URLUtil;

import com.facebook.internal.Validate;
import com.veronica.myjournal.Constants;

/**
 * Created by Veronica on 10/3/2016.
 */
public final class Validator {

    public static boolean validateString(int minLenght,String value){
        return !value.trim().equals("") && value.length() >= minLenght;
    }

    public static boolean isValidEmail(String email){
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean validateLoginUserFields(String email, String password){
        return Validator.isValidEmail(email) && validateString(Constants.PASSWORD_MIN_LENGHT, password);
    }

    public static boolean validateRegisterUserField(String email,String password,String username,String photoUri){
        return Validator.isValidEmail(email) &&
                Validator.validateString(Constants.PASSWORD_MIN_LENGHT, password) &&
                Validator.validateString(Constants.NAME_MIN_LENGHT, username) &&
                Validator.isValidEmail(email) &&
                Validator.isValidUri(photoUri);
    }

    public static boolean validateFacebookRegisterUserField(String email,String username,String photoUri){
        return Validator.isValidEmail(email) &&
                Validator.isValidEmail(email) &&
                Validator.isValidUri(photoUri);
    }



    public static boolean isValidUri(String uri){
        return URLUtil.isValidUrl(uri);
    }
}
