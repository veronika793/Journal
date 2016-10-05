package com.veronica.myjournal.models;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
public class User implements Parcelable {

    private Integer _id;
    private String _email;
    private String _password;
    private String _name;
    private Bitmap _profilePic;

    public User(Integer id, String email,String password, String name, Bitmap photo) {
        this.set_id(id);
        this.set_email(email);
        this.set_password(password);
        this.set_name(name);
        this.set_profilePic(photo);

    }

    public void set_id(Integer id) {
        this._id = id;
    }

    public void set_email(String email) {
        this._email = email;
    }

    public void set_password(String password) {
        this._password = password;
    }

    public void set_name(String name) {
        this._name = name;
    }

    public void set_profilePic(Bitmap profilePic) {
        this._profilePic = profilePic;
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

    public Bitmap get_profilePic() {
        return _profilePic;
    }



    protected User(Parcel in) {
        _id = in.readByte() == 0x00 ? null : in.readInt();
        _email = in.readString();
        _password = in.readString();
        _name = in.readString();
        _profilePic = (Bitmap) in.readValue(Bitmap.class.getClassLoader());
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
        dest.writeValue(_profilePic);
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