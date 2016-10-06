package com.veronica.journal.dbmodels;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

import java.util.List;
public class User extends SugarRecord implements Parcelable {

    String email;
    String password;
    String name;
    String photoUri;

    public User() {

    }

    public User(String email, String password, String name, String photoUri) {
        this.setEmail(email);
        this.setPassword(password);
        this.setName(name);
        this.setPhotoUri(photoUri);
    }

    List<Note> getNotes() {
        return Note.find(Note.class, "user = ?",String.valueOf(getId()));
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPhotoUri() {
        return photoUri;
    }


    protected User(Parcel in) {
        email = in.readString();
        password = in.readString();
        name = in.readString();
        photoUri = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(name);
        dest.writeString(photoUri);
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