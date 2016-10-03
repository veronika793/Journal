package com.veronica.myjournal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.FacebookSdk;
import com.veronica.myjournal.activities.JournalActivity;
import com.veronica.myjournal.activities.RegisterActivity;
import com.veronica.myjournal.app.MyJournalApplication;
import com.veronica.myjournal.fragments.ContainerFragment;
import com.veronica.myjournal.managers.AuthorizationManager;
import com.veronica.myjournal.managers.DialogManager;
import com.veronica.myjournal.managers.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private MyJournalApplication journalApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        this.journalApp = (MyJournalApplication) this.getApplication();
        this.journalApp.setDialogManager(new DialogManager(this));

        startActivity(new Intent(this, RegisterActivity.class));
        if(!AuthorizationManager.getInstance(this).isLoggedIn()){
            startActivity(new Intent(this, RegisterActivity.class));
        }else{
            startActivity(new Intent(this, JournalActivity.class));
        }



    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(!AuthorizationManager.getInstance(this).isLoggedIn()){
            startActivity(new Intent(this, RegisterActivity.class));
        }else{
            startActivity(new Intent(this, JournalActivity.class));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!AuthorizationManager.getInstance(this).isLoggedIn()){
            startActivity(new Intent(this, RegisterActivity.class));
        }else{
            startActivity(new Intent(this, JournalActivity.class));
        }
    }

    //        this.containerFragment = new ContainerFragment();
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.layout_container,containerFragment)
//                .commit();
    }


