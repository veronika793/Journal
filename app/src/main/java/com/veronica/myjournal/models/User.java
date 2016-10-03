package com.veronica.myjournal.models;

import android.net.Uri;

import java.util.Date;

/**
 * Created by Veronica on 9/28/2016.
 */
public class User {

    private Integer _id;
    private String _email;
    private String _password;
    private String _name;
    private String _profilePicUri;
    private boolean _isFacebookUser;

    public User(Integer id, String email,String password, String name, String photo,Boolean isFacebookUser) {
        this.set_id(id);
        this.set_email(email);
        this.set_password(password);
        this.set_name(name);
        this.set_profilePicUri(photo);
        this.set_isFacebookUser(isFacebookUser);

    }

    //facebook user constructor
    public User(Integer id, String email, String name,String photo, boolean isFacebookUser) {
        this(id,email,null,name,photo,isFacebookUser);
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_profilePicUri() {
        return _profilePicUri;
    }

    public void set_profilePicUri(String _profilePicUri) {
        this._profilePicUri = _profilePicUri;
    }

    public boolean is_isFacebookUser() {
        return _isFacebookUser;
    }

    public void set_isFacebookUser(boolean _isFacebookUser) {
        this._isFacebookUser = _isFacebookUser;
    }
}
