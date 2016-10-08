package com.veronica.medaily;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.veronica.medaily.activities.HomeActivity;
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

        if(!appJournal.getAuthManager().isLoggedIn()){
            openRegisterForm();
        }else{
            goToHomeActivity();
        }
    }

    private void openRegisterForm(){
        startActivity(new Intent(MainActivity.this,RegisterActivity.class));
        finish();
    }

    private void goToHomeActivity(){
        startActivity(new Intent(MainActivity.this,HomeActivity.class));
        finish();
    }
}
