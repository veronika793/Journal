package com.veronica.journal.bindingmodels;

import com.veronica.journal.Constants;
import com.veronica.journal.helpers.InputValidator;

import java.util.InvalidPropertiesFormatException;

/**
 * Created by Veronica on 10/4/2016.
 */
public class LoginUserBindingModel {

    private InputValidator _validator;
    private String _email;
    private String _password;

    public LoginUserBindingModel(String email, String password) throws InvalidPropertiesFormatException {
        this._validator = new InputValidator();
        this.set_email(email);
        this.set_password(password);
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String email) throws InvalidPropertiesFormatException {
        if(!_validator.isValidEmail(email)){
            throw  new InvalidPropertiesFormatException("Invalid email");
        }
        this._email = email;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String password) throws InvalidPropertiesFormatException {
        if(!_validator.isMinLenghRestricted(Constants.PASSWORD_MIN_LENGHT,password)){
            throw  new InvalidPropertiesFormatException("Invalid password. Minimum "+Constants.PASSWORD_MIN_LENGHT + " characters");
        }
        this._password = password;
    }
}
