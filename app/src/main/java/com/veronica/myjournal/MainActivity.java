package com.veronica.myjournal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.FacebookSdk;
import com.veronica.myjournal.activities.JournalActivity;
import com.veronica.myjournal.activities.LoginActivity;
import com.veronica.myjournal.activities.RegisterActivity;
import com.veronica.myjournal.app.MyJournalApplication;
import com.veronica.myjournal.fragments.ContainerFragment;
import com.veronica.myjournal.helpers.SmsSender;
import com.veronica.myjournal.managers.AuthorizationManager;
import com.veronica.myjournal.managers.DialogManager;
import com.veronica.myjournal.managers.DiaryDbManager;
import com.veronica.myjournal.managers.UsersDbManager;

public class MainActivity extends AppCompatActivity {

    private MyJournalApplication journalApp;
    private ContainerFragment containerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        this.journalApp = (MyJournalApplication) this.getApplication();
        this.journalApp.setDialogManager(new DialogManager(this));
        UsersDbManager.getInstance(this);
        DiaryDbManager.getInstance(this);
        AuthorizationManager.getInstance(this);

//        SmsSender.SendSms(this,"+359876407847","posleden sms :D da proverq dali taksuva");
        startActivity(new Intent(this, RegisterActivity.class));
//        if(!AuthorizationManager.getInstance(this).isLoggedIn()){
//            startActivity(new Intent(this, RegisterActivity.class));
//        }else{
//            startActivity(new Intent(this, JournalActivity.class));
//        }



    }


//        this.containerFragment = new ContainerFragment();
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.layout_container,containerFragment)
//                .commit();
    }


