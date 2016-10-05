package com.veronica.myjournal.database;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Veronica on 10/5/2016.
 */
public abstract class DbManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "journal_db";

    private static final int DATABASE_VERSION =1;

    public DbManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
