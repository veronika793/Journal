package com.veronica.myjournal.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Veronica on 9/28/2016.
 */
public class User implements Parcelable {

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

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public void set_profilePicUri(String _profilePicUri) {
        this._profilePicUri = _profilePicUri;
    }

    public void set_isFacebookUser(boolean _isFacebookUser) {
        this._isFacebookUser = _isFacebookUser;
    }

    public Integer get_id() {
        return _id;
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

    protected User(Parcel in) {
        _id = in.readByte() == 0x00 ? null : in.readInt();
        _email = in.readString();
        _password = in.readString();
        _name = in.readString();
        _profilePicUri = in.readString();
        _isFacebookUser = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (_id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(_id);
        }
        dest.writeString(_email);
        dest.writeString(_password);
        dest.writeString(_name);
        dest.writeString(_profilePicUri);
        dest.writeByte((byte) (_isFacebookUser ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}