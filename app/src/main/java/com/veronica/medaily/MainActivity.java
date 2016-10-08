package com.veronica.medaily;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.veronica.medaily.activities.LoginActivity;
import com.veronica.medaily.R;
import com.veronica.medaily.activities.RegisterActivity;

public class MainActivity extends AppCompatActivity {

    private MainApplication appJournal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.appJournal = (MainApplication) this.getApplication();
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
