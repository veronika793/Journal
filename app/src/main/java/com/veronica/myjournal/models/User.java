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

    public User(Integer id, String email, String name, String profilePic, String password,Boolean isFacebookUser) {
        this._id = id;
        this._email = email;
        this._name = name;
        this._profilePicUri = profilePic;
        this._password = password;
        this._isFacebookUser = isFacebookUser;
    }

    public User(String email, String name, String profilePicUri, String password,Boolean isFacebookUser) {
        this(null,email,name,profilePicUri,password,isFacebookUser);

    }
}
