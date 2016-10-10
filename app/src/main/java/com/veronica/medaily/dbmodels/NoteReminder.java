package com.veronica.medaily.dbmodels;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

/**
 * Created by Veronica on 10/8/2016.
 */
public class NoteReminder extends SugarRecord implements Parcelable {

    private User user;
    private Note note;
    private String startTime;

    public NoteReminder() {
    }

    public NoteReminder(User user,Note note, String startTime) {
        this.user = user;
        this.note = note;
        this.startTime = startTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }


    protected NoteReminder(Parcel in) {
        user = (User) in.readValue(User.class.getClassLoader());
        note = (Note) in.readValue(Note.class.getClassLoader());
        startTime = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(user);
        dest.writeValue(note);
        dest.writeString(startTime);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<NoteReminder> CREATOR = new Parcelable.Creator<NoteReminder>() {
        @Override
        public NoteReminder createFromParcel(Parcel in) {
            return new NoteReminder(in);
        }

        @Override
        public NoteReminder[] newArray(int size) {
            return new NoteReminder[size];
        }
    };
}