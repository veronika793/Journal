package com.veronica.myjournal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.FacebookSdk;
import com.veronica.myjournal.activities.LoginActivity;
import com.veronica.myjournal.activities.RegisterActivity;
import com.veronica.myjournal.app.MyJournalApplication;

public class MainActivity extends AppCompatActivity {

    private MyJournalApplication appJournal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.appJournal = (MyJournalApplication) this.getApplication();
        this.appJournal.setAuthManager();
        this.appJournal.setNotesDbManager();
        this.appJournal.setUsersDbManager();

//        appJournal.getUserDbManager().onUpgrade(appJournal.getUserDbManager().getWritableDatabase(),1,1);
//        appJournal.getAuthManager().logoutUser();
        //TODO 1: extract more constants

        startActivity(new Intent(this, RegisterActivity.class));
        if(!appJournal.getAuthManager().isLoggedIn()){
            openRegisterForm();
        }else{
            goToJournalActivity();
        }


    }

    private void openRegisterForm(){
        startActivity(new Intent(MainActivity.this,RegisterActivity.class));
        finish();
    }

    private void goToJournalActivity(){
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
        finish();
    }
}


