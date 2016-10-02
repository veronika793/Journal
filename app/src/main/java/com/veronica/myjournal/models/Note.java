package com.veronica.myjournal.models;

import android.graphics.Bitmap;

import com.veronica.myjournal.toberemoved.JournalDateFormat;

/**
 * Created by Veronica on 9/30/2016.
 */
public class Note {

    private Integer _id;
    private Integer _userId;
    private String _title;
    private String _content;
    private JournalDateFormat _date;
    private Bitmap _photo;
    private String _location;
}
