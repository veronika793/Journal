package com.veronica.medaily.validationmodels;

import com.veronica.medaily.Constants;
import com.veronica.medaily.helpers.InputValidator;

import java.util.InvalidPropertiesFormatException;

public class UserValidationModel {

    private String _email;
    private String _password;
    private String _name;
    private String _profilePic;

    public UserValidationModel(String email, String password, String name, String photoUri) throws InvalidPropertiesFormatException {
        this.set_email(email);
        this.set_password(password);
        this.set_name(name);
        this.set_profilePic(photoUri);

    }

    public void set_email(String email) throws InvalidPropertiesFormatException {
        if(!InputValidator.isValidEmail(email)){
            throw  new InvalidPropertiesFormatException("Invalid email");
        }
        this._email = email;
    }

    public void set_password(String password) throws InvalidPropertiesFormatException {
        if(!InputValidator.isMinLenghRestricted(Constants.PASSWORD_MIN_LENGTH,password)){
            throw  new InvalidPropertiesFormatException("Invalid password. Minimum "+Constants.PASSWORD_MIN_LENGTH + " characters");

        }
        this._password = password;
    }

    public void set_name(String name) throws InvalidPropertiesFormatException {
        if(!InputValidator.isMinLenghRestricted(Constants.NAME_MIN_LENGTH,name)){
            throw  new InvalidPropertiesFormatException("Invalid name. Minimum "+Constants.NAME_MIN_LENGTH + " characters");
        }
        this._name = name;
    }

    public void set_profilePic(String profilePic) throws InvalidPropertiesFormatException {

        if(!InputValidator.isValidUri(profilePic)){
            throw new InvalidPropertiesFormatException("Please select profile picture");
        }
        this._profilePic = profilePic;
    }

    public String get_email() {
        return _email;
    }

    public String get_password() {
        return _password;
    }

    public String get_name() {
        return _name;
    }

    public String get_profilePicUri() {
        return _profilePic;
    }

}