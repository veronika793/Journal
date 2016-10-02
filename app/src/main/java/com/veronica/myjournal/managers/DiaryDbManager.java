package com.veronica.myjournal.managers;

import android.content.Context;

import com.veronica.myjournal.models.Note;

/**
 * Created by Veronica on 10/2/2016.
 */
public class DiaryDbManager {
    private static DiaryDbManager mInstance;
    private Context mContext;

    public static DiaryDbManager getInstance(Context context){
        if(mInstance==null){
            return new DiaryDbManager(context);
        }
        return mInstance;
    }

    private DiaryDbManager(Context context) {
        mContext = context;
    }


    public void addNewNote(Note note){

    }

    public void deleteNote(Note note){

    }

    public void getNote(Integer id){

    }

}
