package com.veronica.medaily.helpers;

/**
 * Created by Veronica on 10/5/2016.
 * generates key generator for eaach user so even if the passwords are the same, hashed password is different
 */

public class KeyGenerator {

    public static String generateKey(String value){
        if(value.length()>8){
            return value.substring(0, Math.min(value.length(), 8));
        }
        return String.format("%1$"+8+ "s", value).replace(' ','0');

    }
}
