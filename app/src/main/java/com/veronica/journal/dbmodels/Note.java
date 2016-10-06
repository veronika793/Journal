package com.veronica.journal.dbmodels;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;
public class Note extends SugarRecord implements Parcelable {

    User user;
    String title;
    String content;
    String createdOn;
    String photoUri;
    String location;

    public Note(User user, String title, String content, String createdOn, String photoUri, String location) {
        this.setUser(user);
        this.setTitle(title);
        this.setContent(content);
        this.setCreatedOn(createdOn);
        this.setPhotoUri(photoUri);
        this.setLocation(location);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhotoUri() {
        return this.photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    protected Note(Parcel in) {
        user = (User) in.readValue(User.class.getClassLoader());
        title = in.readString();
        content = in.readString();
        createdOn = in.readString();
        photoUri = in.readString();
        location = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(user);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(createdOn);
        dest.writeString(photoUri);
        dest.writeString(location);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Note> CREATOR = new Parcelable.Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}