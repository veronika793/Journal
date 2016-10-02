package com.veronica.myjournal.managers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.SimpleDateFormat;

import com.veronica.myjournal.models.User;

/**
 * Created by Veronica on 10/2/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper{
    private static DatabaseHelper mInstance;
    private Context mContext;

    private static final String DATABASE_NAME = "MyJournal";

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
    private static final String NOTE_CREATED_ON = "note_created_on";
    private static final String NOTE_CONTENT = "note_content";
    private static final String NOTE_PHOTO_URI = "note_photo";
    private static final String NOTE_LOCATION = "note_location";



    // Database creation sql statement
    public static final String create_users_table= "CREATE TABLE "+ USERS_TABLE + " ( "+USER_KEY_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+USER_EMAIL+" TEXT NOT NULL UNIQUE, "+USER_PASSWORD+ " TEXT, "+USER_NAME+ " TEXT NOT NULL, "+USER_PHOTO_URI+ " TEXT NOT NULL, "+ USER_IS_FACEBOOK+ " TEXT NOT NULL)";
    public static final String create_notes_table = "CREATE TABLE "+ NOTES_TABLE + " ( "+NOTE_KEY_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+NOTE_USER_ID+" INTEGER, "+ "FOREIGN KEY ( "+NOTE_USER_ID+" ) REFERENCES "+USERS_TABLE +"("+USER_KEY_ID+"), "+NOTE_TITLE+ " TEXT NOT NULL, "+ NOTE_CONTENT + " TEXT NOT NULL, "+NOTE_PHOTO_URI+ " TEXT, "+ NOTE_LOCATION+ " TEXT, "+ NOTE_CREATED_ON+ " TEXT NOT NULL)";



    public static DatabaseHelper getInstance(Context context){
        if(mInstance==null){
            return new DatabaseHelper(context);
        }
        return mInstance;
    }

    private DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_users_table);
        db.execSQL(create_notes_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void getUserData(String email){
        //find user by email from db...

    }

    public boolean checkIfUserExists(String email){
        // check if user with the same email exists
        return true;
    }

    public boolean isFacebookUser(){
        return false;
    }

    public void registerUser(User user){
        //register user into database..

        AuthorizationManager.getInstance(mContext).setLogin(true);
    }

    public void changeUserPassword(String email){

    }

}
