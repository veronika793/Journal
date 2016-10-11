package com.veronica.medaily.validationmodels;

import com.veronica.medaily.Constants;
import com.veronica.medaily.helpers.InputValidator;

import java.util.InvalidPropertiesFormatException;

/**
 * Created by Veronica on 10/4/2016.
 */
public class LoginUserValidationModel {

    private String _email;
    private String _password;

    public LoginUserValidationModel(String email, String password) throws InvalidPropertiesFormatException {
        this.set_email(email);
        this.set_password(password);
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String email) throws InvalidPropertiesFormatException {
        if(!InputValidator.isValidEmail(email)){
            throw  new InvalidPropertiesFormatException("Invalid email");
        }
        this._email = email;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String password) throws InvalidPropertiesFormatException {
        if(!InputValidator.isMinLenghRestricted(Constants.PASSWORD_MIN_LENGTH,password)){
            throw  new InvalidPropertiesFormatException("Invalid password. Minimum "+Constants.PASSWORD_MIN_LENGTH + " characters");
        }
        this._password = password;
    }
}
