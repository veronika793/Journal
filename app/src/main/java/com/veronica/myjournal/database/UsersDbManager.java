package com.veronica.myjournal.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.veronica.myjournal.bindingmodels.UserBindingModel;
import com.veronica.myjournal.helpers.DbBitmapUtility;
import com.veronica.myjournal.interfaces.ICRUDDbOperations;
import com.veronica.myjournal.models.User;

import java.util.ArrayList;
import java.util.List;

public class UsersDbManager extends DbManager implements ICRUDDbOperations<User,UserBindingModel> {
    private Context mContext;

    public static final String USERS_TABLE  =  "users";
    public static final String USER_KEY_ID = "user_id";

    private static final String USER_EMAIL = "user_email";
    private static final String USER_PASSWORD = "user_password";
    private static final String USER_NAME = "user_name";
    private static final String USER_PHOTO = "user_photo";

    // Database creation sql statement
    public static final String create_users_table= "CREATE TABLE "+ USERS_TABLE + " ( "+USER_KEY_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+USER_EMAIL+" TEXT NOT NULL UNIQUE, "+USER_PASSWORD+ " TEXT, "+USER_NAME+ " TEXT NOT NULL, "+USER_PHOTO+ " BLOB);";

    public UsersDbManager(Context context) {
        super(context);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_users_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+USERS_TABLE);
        onCreate(db);
    }

    public boolean checkIfExists(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(USERS_TABLE,new String[]{USER_EMAIL},USER_EMAIL+" =? ",new String[]{email},null,null,null);
        int cursorCount = cursor.getCount();
        if(cursorCount>0){
            cursor.close();
            return  true;
        }
        cursor.close();
        return false;
    }

    public User getByEmail(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        User user;
        Cursor cursor = db.query(USERS_TABLE,new String[]{USER_KEY_ID,USER_EMAIL,USER_PASSWORD,USER_NAME,USER_PHOTO},USER_EMAIL+" =? ",new String[]{email},null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
            Integer userId = cursor.getInt(0);
            String userEmail = cursor.getString(1);
            String userPassword = cursor.getString(2);
            String userName = cursor.getString(3);
            byte[] userPhotoByteArr = cursor.getBlob(4);

            Bitmap photo = DbBitmapUtility.getImage(userPhotoByteArr);
            user = new User(userId,userEmail,userPassword,userName,photo);
            cursor.close();
            db.close();
            return user;
        }
        db.close();
        return null;
    }

   public boolean isLoginValid(String email,String password){
       SQLiteDatabase db = this.getWritableDatabase();
       Cursor cursor = db.query(USERS_TABLE,new String[]{USER_EMAIL,USER_PASSWORD},USER_EMAIL+" =? AND "+USER_PASSWORD+" =? ",new String[]{email,password},null,null,null);
       if(cursor.getCount() <= 0){
           cursor.close();
           return false;
       }
       cursor.close();
       return true;
   }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public boolean insert(UserBindingModel user) {

        byte[] image = DbBitmapUtility.getBytes(user.get_profilePicUri());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues insertValues = new ContentValues();

        insertValues.put(USER_EMAIL,user.get_email() );
        insertValues.put(USER_PASSWORD,user.get_password());
        insertValues.put(USER_NAME,user.get_name() );
        insertValues.put(USER_PHOTO,image);

        long response = db.insert(USERS_TABLE,null,insertValues);
        db.close();
        return response > 0;
    }

    @Override
    public boolean update(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues insertValues = new ContentValues();
        byte[] image = DbBitmapUtility.getBytes(user.get_profilePic());

        if(user.get_email()!=null){
            insertValues.put(USER_EMAIL,user.get_email() );
        }
        if(user.get_name()!=null){
            insertValues.put(USER_NAME,user.get_name() );
        }
        if(user.get_password()!=null){
            insertValues.put(USER_PASSWORD,user.get_password());
        }
        if(user.get_profilePic()!=null){
            insertValues.put(USER_PHOTO,image);
        }
        long response = db.update(USERS_TABLE,insertValues,USER_EMAIL + " =? ",new String[]{user.get_email()});
        db.close();
        return response > 0;
    }

    @Override
    public boolean delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long response = db.delete(USERS_TABLE,USER_KEY_ID+" =? ",new String[]{String.valueOf(id)});
        db.close();
        return response > 0;
    }

    @Override
    public long itemsCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long itemsCount= DatabaseUtils.queryNumEntries(db, USERS_TABLE);
        db.close();
        return itemsCount;
    }
}
