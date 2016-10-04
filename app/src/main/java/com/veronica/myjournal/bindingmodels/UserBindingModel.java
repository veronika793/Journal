package com.veronica.myjournal.bindingmodels;

import android.os.Parcel;
import android.os.Parcelable;

import com.veronica.myjournal.Constants;
import com.veronica.myjournal.helpers.InputValidator;

import java.util.InvalidPropertiesFormatException;

/**
 * Created by Veronica on 9/28/2016.
 */
public class UserBindingModel{

    InputValidator _validator;
    private String _email;
    private String _password;
    private String _name;
    private String _profilePicUri;
    private boolean _isFacebookUser;

    public UserBindingModel(String email, String password, String name, String photo, Boolean isFacebookUser) throws InvalidPropertiesFormatException {
        this._validator = new InputValidator();
        this.set_email(email);
        this.set_password(password);
        this.set_name(name);
        this.set_profilePicUri(photo);
        this.set_isFacebookUser(isFacebookUser);

    }

    //facebook user constructor
    public UserBindingModel(String email, String name, String photo, boolean isFacebookUser) throws InvalidPropertiesFormatException {
        this._validator = new InputValidator();
        this.set_email(email);
        this.set_name(name);
        this.set_profilePicUri(photo);
        this.set_isFacebookUser(isFacebookUser);
    }

    public void set_email(String email) throws InvalidPropertiesFormatException {
        if(!_validator.isValidEmail(email)){
            throw  new InvalidPropertiesFormatException("Invalid email");
        }
        this._email = email;
    }

    public void set_password(String password) throws InvalidPropertiesFormatException {
        if(!_validator.isMinLenghRestricted(Constants.PASSWORD_MIN_LENGHT,password)){
            throw  new InvalidPropertiesFormatException("Invalid password. Minimum "+Constants.PASSWORD_MIN_LENGHT + " characters");

        }
        this._password = password;
    }

    public void set_name(String name) throws InvalidPropertiesFormatException {
        if(!_validator.isMinLenghRestricted(Constants.NAME_MIN_LENGHT,name)){
            throw  new InvalidPropertiesFormatException("Invalid name. Minimum "+Constants.NAME_MIN_LENGHT + " characters");
        }
        this._name = name;
    }

    public void set_profilePicUri(String _profilePicUri) {
        this._profilePicUri = _profilePicUri;
    }

    public void set_isFacebookUser(boolean _isFacebookUser) {
        this._isFacebookUser = _isFacebookUser;
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
        return _profilePicUri;
    }

    public boolean is_isFacebookUser() {
        return _isFacebookUser;
    }


}