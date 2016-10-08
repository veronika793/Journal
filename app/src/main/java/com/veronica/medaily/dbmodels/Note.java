package com.veronica.medaily.dbmodels;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;
public class Note extends SugarRecord implements Parcelable {

    User user;
    Category category;
    String title;
    String content;
    String createdOnDate;
    String reminderDate;
    String photoUri;
    String location;

    public Note(){

    }

    public Note(Category category, User user, String title, String content, String createdOn,String reminderDate, String photoUri, String location) {
        this.category = category;
        this.user = user;
        this.title = title;
        this.content = content;
        this.createdOnDate = createdOn;
        this.photoUri = photoUri;
        this.location = location;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedOnDate(String createdOnDate) {
        this.createdOnDate = createdOnDate;
    }

    public void setReminderDate(String reminderDate) {
        this.reminderDate = reminderDate;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return this.title;
    }

    public String getCreatedOnDate() {
        return this.createdOnDate;
    }

    public String getContent() {
        return this.content;
    }

    public String getPhotoUri() {
        return this.photoUri;
    }

    public String getLocation() {
        return this.location;
    }

    public Category getCategory() {
        return category;
    }

    public String getReminderDate() {
        return reminderDate;
    }



    protected Note(Parcel in) {
        user = (User) in.readValue(User.class.getClassLoader());
        category = (Category) in.readValue(Category.class.getClassLoader());
        title = in.readString();
        content = in.readString();
        createdOnDate = in.readString();
        reminderDate = in.readString();
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
        dest.writeValue(category);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(createdOnDate);
        dest.writeString(reminderDate);
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