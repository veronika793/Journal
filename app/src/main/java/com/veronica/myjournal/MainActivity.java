package com.veronica.myjournal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.FacebookSdk;
import com.veronica.myjournal.activities.LoginActivity;
import com.veronica.myjournal.app.MyJournalApplication;
import com.veronica.myjournal.fragments.ContainerFragment;
import com.veronica.myjournal.managers.DialogManager;
import com.veronica.myjournal.managers.LoginManager;
import com.veronica.myjournal.managers.SessionManager;

public class MainActivity extends AppCompatActivity {

    private MyJournalApplication journalApp;
    private ContainerFragment containerFragment;
    private LoginManager loginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        this.loginManager = LoginManager.getInstance(this);

        this.journalApp = (MyJournalApplication)this.getApplication();
        this.journalApp.setDialogManager(new DialogManager(this));
        this.journalApp.setSessionManager(new SessionManager(this));


        startActivity(new Intent(this, LoginActivity.class));

//        this.containerFragment = new ContainerFragment();
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.layout_container,containerFragment)
//                .commit();
    }
}
