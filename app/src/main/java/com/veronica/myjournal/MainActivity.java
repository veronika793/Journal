package com.veronica.myjournal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.FacebookSdk;
import com.veronica.myjournal.activities.LoginActivity;
import com.veronica.myjournal.activities.RegisterActivity;
import com.veronica.myjournal.app.MyJournalApplication;
import com.veronica.myjournal.managers.AuthorizationManager;
import com.veronica.myjournal.managers.DialogManager;

public class MainActivity extends AppCompatActivity {

    private MyJournalApplication appJournal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.appJournal = (MyJournalApplication) this.getApplication();
        this.appJournal.setDialogManager(new DialogManager(this));

        //appJournal.getDbManager().onUpgrade(appJournal.getDbManager().getWritableDatabase(),1,1);

        //TODO 1: extract more constants

        startActivity(new Intent(this, RegisterActivity.class));
        if(!AuthorizationManager.getInstance(this).isLoggedIn()){
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


