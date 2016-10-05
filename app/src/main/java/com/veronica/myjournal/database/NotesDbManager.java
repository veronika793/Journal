package com.veronica.myjournal.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.veronica.myjournal.bindingmodels.NoteBindingModel;
import com.veronica.myjournal.interfaces.ICRUDDbOperation;
import com.veronica.myjournal.models.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Veronica on 10/5/2016.
 */
public class NotesDbManager extends DbManager implements ICRUDDbOperation<Note,NoteBindingModel> {

    private static final String NOTE_KEY_ID = "note_id";
    private static final String NOTES_TABLE  =  "notes";
    private static final String NOTE_USER_ID = "note_user_id";
    private static final String NOTE_TITLE = "note_title";
    private static final String NOTE_CONTENT = "note_content";
    private static final String NOTE_PHOTO_URI = "note_photo";
    private static final String NOTE_LOCATION = "note_location";
    private static final String NOTE_CREATED_ON = "note_created_on";

    public static final String create_notes_table = "CREATE TABLE "+ NOTES_TABLE + " ( "+NOTE_KEY_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+NOTE_USER_ID+" INTEGER, "+NOTE_TITLE+ " TEXT NOT NULL, "+ NOTE_CONTENT + " TEXT NOT NULL, "+NOTE_PHOTO_URI+ " TEXT, "+ NOTE_LOCATION+ " TEXT, "+ NOTE_CREATED_ON+ " TEXT NOT NULL, "+"FOREIGN KEY "+"("+NOTE_USER_ID+")"+" REFERENCES "+UsersDbManager.USERS_TABLE+"("+UsersDbManager.USER_KEY_ID+"));";

    public NotesDbManager(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_notes_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+NOTES_TABLE);
        onCreate(db);
    }

    @Override
    public List<Note> getAll() {
        return null;
    }

    @Override
    public boolean insert(NoteBindingModel note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues insertValues = new ContentValues();

        insertValues.put(NOTE_USER_ID,note.get_user_id() );
        insertValues.put(NOTE_TITLE,note.get_title());
        insertValues.put(NOTE_CONTENT,note.get_content());
        insertValues.put(NOTE_PHOTO_URI,note.get_photo());
        insertValues.put(NOTE_LOCATION,note.get_location());
        insertValues.put(NOTE_CREATED_ON,note.get_created_on());

        long response = db.insert(NOTES_TABLE,null,insertValues);
        db.close();
        return response > 0;
    }

    @Override
    public boolean update(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues insertValues = new ContentValues();

        if(note.get_title()!=null){
            insertValues.put(NOTE_TITLE,note.get_title() );
        }
        if(note.get_content()!=null){
            insertValues.put(NOTE_CONTENT,note.get_content() );
        }
        if(note.get_photo()!=null){
            insertValues.put(NOTE_PHOTO_URI,note.get_photo() );
        }
        if(note.get_location()!=null){
            insertValues.put(NOTE_LOCATION,note.get_location() );
        }

        long response = db.update(NOTES_TABLE,insertValues,NOTE_KEY_ID + " =? ",new String[]{String.valueOf(note.get_id())});
        db.close();
        return response > 0;
    }

    @Override
    public boolean delete(int noteId) {
        SQLiteDatabase db = this.getWritableDatabase();
        long response = db.delete(NOTES_TABLE,NOTE_KEY_ID+" =? ",new String[]{String.valueOf(noteId)});
        db.close();
        return response > 0;
    }

    @Override
    public long itemsCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long itemsCount= DatabaseUtils.queryNumEntries(db, NOTES_TABLE);
        db.close();
        return itemsCount;
    }

    public List<Note> getAllForUser(Integer userId){
        List<Note> notes = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();
        try {
            String rawQuery = "SELECT * FROM " +NOTES_TABLE + " WHERE "+NOTE_USER_ID +" = "+ userId;
            Cursor notesCursor = db.rawQuery(rawQuery,null);

            if(notesCursor!=null){
                while (notesCursor.moveToNext()){
                    Integer note_user_id = notesCursor.getInt(1);
                    String noteTitle = notesCursor.getString(2);
                    String noteContent = notesCursor.getString(3);
                    String notePhoto = notesCursor.getString(4);
                    String noteLocation = notesCursor.getString(5);
                    String noteCreatedOn = notesCursor.getString(6);

                    Note note = new Note(null,note_user_id,noteTitle,noteContent,noteCreatedOn,notePhoto,noteLocation);
                    notes.add(note);
                }
                notesCursor.close();
                db.setTransactionSuccessful();
            }
        }finally {
            db.endTransaction();
        }
        db.close();
        return notes;
    }

}
