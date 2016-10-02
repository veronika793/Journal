package com.veronica.myjournal.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.veronica.myjournal.R;
import com.veronica.myjournal.app.MyJournalApplication;
import com.veronica.myjournal.managers.LoginManager;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private MyJournalApplication journalApp = (MyJournalApplication)this.getApplication();

    private CallbackManager callbackManager = null;
    private AccessTokenTracker accessTokenTracker = null;
    private ProfileTracker profileTracker;

    public static final String PARCEL_KEY = "parcel_key";
    private LoginButton fbLoginButton;
    private LoginManager loginManager;

    FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {

        @Override
        public void onSuccess(LoginResult loginResult) {

        }

        @Override
        public void onCancel() {
            Log.d("DEBUG", "No net");
        }

        @Override
        public void onError(FacebookException error) {
            Log.d("DEBUG", "NO NET");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }

    public boolean accessTokenIsValid(AccessToken token){
        return true;
    }

    private void updateUserAndLaunch(LoginActivity loginActivity) {
    }

    private void launchApp() {
        startActivity(new Intent(LoginActivity.this,JournalActivity.class));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    public void requestLogin(){
        startActivity(new Intent(LoginActivity.this,LoginActivity.class));
    }
}
