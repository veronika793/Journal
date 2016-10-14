package com.veronica.medaily;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.veronica.medaily.activities.HomeActivity;
import com.veronica.medaily.activities.LoginActivity;
import com.veronica.medaily.R;
import com.veronica.medaily.activities.RegisterActivity;
import com.veronica.medaily.helpers.NotificationHandler;

public class MainActivity extends AppCompatActivity {

    private MainApplication mainApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationHandler.setupNotifier(this);
        NotificationHandler.getInstance();

        this.mainApp = (MainApplication) this.getApplication();
        this.mainApp.setAuthManager();

        if(!mainApp.getAuthManager().isLoggedIn()){
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
