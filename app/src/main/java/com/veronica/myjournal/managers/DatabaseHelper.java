package com.veronica.myjournal.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.SimpleDateFormat;

import com.veronica.myjournal.models.Note;
import com.veronica.myjournal.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Veronica on 10/2/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper{
    private Context mContext;

    private static final String DATABASE_NAME = "journal_db";

    private static final int DATABASE_VERSION =1;

    private static final String USERS_TABLE  =  "users";
    private static final String NOTES_TABLE  =  "notes";

    private static final String USER_KEY_ID = "user_id";
    private static final String USER_EMAIL = "user_email";
    private static final String USER_PASSWORD = "user_password";
    private static final String USER_NAME = "user_name";
    private static final String USER_PHOTO_URI = "user_photo";
    private static final String USER_IS_FACEBOOK = "is_fb_user";

    private static final String NOTE_KEY_ID = "note_id";
    private static final String NOTE_USER_ID = "note_user_id";
    private static final String NOTE_TITLE = "note_title";
    private static final String NOTE_CONTENT = "note_content";
    private static final String NOTE_PHOTO_URI = "note_photo";
    private static final String NOTE_LOCATION = "note_location";
    private static final String NOTE_CREATED_ON = "note_created_on";

    // Database creation sql statement
    public static final String create_users_table= "CREATE TABLE "+ USERS_TABLE + " ( "+USER_KEY_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+USER_EMAIL+" TEXT NOT NULL UNIQUE, "+USER_PASSWORD+ " TEXT, "+USER_NAME+ " TEXT NOT NULL, "+USER_PHOTO_URI+ " TEXT NOT NULL, "+ USER_IS_FACEBOOK+ " BOOLEAN NOT NULL);";
    public static final String create_notes_table = "CREATE TABLE "+ NOTES_TABLE + " ( "+NOTE_KEY_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+NOTE_USER_ID+" INTEGER, "+NOTE_TITLE+ " TEXT NOT NULL, "+ NOTE_CONTENT + " TEXT NOT NULL, "+NOTE_PHOTO_URI+ " TEXT, "+ NOTE_LOCATION+ " TEXT, "+ NOTE_CREATED_ON+ " TEXT NOT NULL, "+"FOREIGN KEY "+"("+NOTE_USER_ID+")"+" REFERENCES "+USERS_TABLE+"("+USER_KEY_ID+"));";


    public DatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = ctx;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_users_table);
        db.execSQL(create_notes_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+USERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+NOTES_TABLE);

        onCreate(db);
    }

    public boolean addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues insertValues = new ContentValues();
        if(user.is_isFacebookUser()){
            insertValues.put(USER_EMAIL,user.get_email() );
            insertValues.put(USER_NAME,user.get_name() );
            insertValues.put(USER_PHOTO_URI,user.get_profilePicUri());
            insertValues.put(USER_IS_FACEBOOK,user.is_isFacebookUser());

        }else{
            insertValues.put(USER_EMAIL,user.get_email() );
            insertValues.put(USER_PASSWORD,user.get_password());
            insertValues.put(USER_NAME,user.get_name() );
            insertValues.put(USER_PHOTO_URI,user.get_profilePicUri());
            insertValues.put(USER_IS_FACEBOOK,user.is_isFacebookUser());
        }
        long response = db.insert(USERS_TABLE,null,insertValues);
        db.close();
        return response > 0;
    }

    public boolean deleteUser(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        long response = db.delete(USERS_TABLE,USER_EMAIL+" =? ",new String[]{email});
        db.close();
        return response > 0;
    }

    public void updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues insertValues = new ContentValues();


        if(user.get_email()!=null){
            insertValues.put(USER_EMAIL,user.get_email() );
        }
        if(user.get_name()!=null){
            insertValues.put(USER_NAME,user.get_name() );
        }
        if(user.get_password()!=null){
            insertValues.put(USER_PASSWORD,user.get_password());
        }
        if(user.get_profilePicUri()!=null){
            insertValues.put(USER_PHOTO_URI,user.get_profilePicUri());
        }
        db.update(USERS_TABLE,insertValues,USER_EMAIL + " =? ",new String[]{user.get_email()});
        db.close();
    }

    public List<User> getAllUsers(){
        List<User> users = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();
        try {
            String rawQuery = "SELECT * FROM " +USERS_TABLE;
            Cursor usersCursor = db.rawQuery(rawQuery,null);

            if(usersCursor!=null){
                while (usersCursor.moveToNext()){
                    Integer userId = usersCursor.getInt(0);
                    String userEmail = usersCursor.getString(1);
                    String userPassword = usersCursor.getString(2);
                    String userName = usersCursor.getString(3);
                    String userPhoto = usersCursor.getString(4);
                    Boolean userIsFb = usersCursor.getInt(5)>0;

                    User user = new User(userId,userEmail,userPassword,userName,userPhoto,userIsFb);
                    users.add(user);
                }
                usersCursor.close();
                db.setTransactionSuccessful();
            }
        }finally {
            db.endTransaction();
        }
        db.close();
        return users;
    }

    public boolean checkIfUserExists(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM " + USERS_TABLE + " WHERE " + USER_EMAIL + " = " + email;
        Cursor cursor = db.query(USERS_TABLE,new String[]{USER_EMAIL},USER_EMAIL+" =? ",new String[]{email},null,null,null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public User getUserByEmail(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM " + USERS_TABLE + " WHERE " + USER_EMAIL + " = " + email;
        User user;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor!=null){
            cursor.moveToFirst();
            Integer userId = cursor.getInt(0);
            String userEmail = cursor.getString(1);
            String userPassword = cursor.getString(2);
            String userName = cursor.getString(3);
            String userPhoto = cursor.getString(4);
            Boolean userIsFb = cursor.getInt(5)>0;

            user = new User(userId,userEmail,userPassword,userName,userPhoto,userIsFb);
            cursor.close();
            db.close();
            return user;
        }
        db.close();
        return null;
    }

   public boolean checkIfUserExist(String email,String password){
       SQLiteDatabase db = this.getWritableDatabase();
//       String rawQuery = "SELECT * FROM " + USERS_TABLE + " WHERE " + USER_EMAIL + " = " + email + " AND " + USER_PASSWORD + " = "+password;
//       Cursor cursor = db.rawQuery(rawQuery, null);
       Cursor cursor = db.query(USERS_TABLE,new String[]{USER_EMAIL,USER_PASSWORD},USER_EMAIL+" =? AND "+USER_PASSWORD+" =? ",new String[]{email,password},null,null,null);
       if(cursor.getCount() <= 0){
           cursor.close();
           return false;
       }
       cursor.close();
       return true;
   }

    public long getUsersCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        long itemsCount= DatabaseUtils.queryNumEntries(db, USERS_TABLE);
        db.close();
        return itemsCount;
    }

    public boolean addNote(Note note){
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

    public boolean deleteNote(Integer noteId){
        SQLiteDatabase db = this.getWritableDatabase();
        long response = db.delete(NOTES_TABLE,NOTE_KEY_ID+" =? ",new String[]{String.valueOf(noteId)});
        db.close();
        return response > 0;
    }

    public void updateNote(Note note){
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

        db.update(NOTES_TABLE,insertValues,NOTE_KEY_ID + " =? ",new String[]{String.valueOf(note.get_id())});
        db.close();
    }

    public List<Note> getAllNotesForUser(Integer userId){
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

    public long getNotesCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        long itemsCount= DatabaseUtils.queryNumEntries(db, NOTES_TABLE);
        db.close();
        return itemsCount;

    }

}
