package com.veronica.journal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.veronica.journal.activities.LoginActivity;
import com.veronica.journal.activities.RegisterActivity;

public class MainActivity extends AppCompatActivity {

    private JournalApp appJournal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.appJournal = (JournalApp) this.getApplication();
        this.appJournal.setAuthManager();

        //this.appJournal.getAuthManager().logoutUser();
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
