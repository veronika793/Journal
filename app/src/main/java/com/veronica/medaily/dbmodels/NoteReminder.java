package com.veronica.medaily.dbmodels;

import com.orm.SugarRecord;

/**
 * Created by Veronica on 10/8/2016.
 */
public class NoteReminder extends SugarRecord {
    private Note note;
    private String startTime;

    public NoteReminder(Note note, String startTime) {
        this.note = note;
        this.startTime = startTime;
    }

    public NoteReminder() {
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
}
