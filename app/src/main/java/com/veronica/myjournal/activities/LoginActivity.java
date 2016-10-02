package com.veronica.myjournal.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.veronica.myjournal.R;
import com.veronica.myjournal.app.MyJournalApplication;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private MyJournalApplication journalApp = (MyJournalApplication)this.getApplication();

    //facebook login props
    private CallbackManager callbackManager = null;
    private AccessTokenTracker accessTokenTracker = null;
    private ProfileTracker profileTracker;
    public static final String PARCEL_KEY = "parcel_key";
    private LoginButton fbLoginButton;

    //users login props
    private EditText mEditTxtUserEmail;
    private EditText mEditTxtUserPassword;
    private Button mBtnOpenRegisterForm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //facebook login ..
//        callbackManager = CallbackManager.Factory.create();
//        fbLoginButton = (LoginButton) findViewById(R.id.btn_login_fb);
//        fbLoginButton.setReadPermissions(Arrays.asList("public_profile","email"));
//        fbLoginButton.setOnClickListener(this);

        //users login..
        mEditTxtUserEmail = (EditText) findViewById(R.id.edit_txt_login_email);
        mEditTxtUserPassword = (EditText) findViewById(R.id.edit_txt_login_password);
        mBtnOpenRegisterForm = (Button) findViewById(R.id.btn_open_login_form);

        mBtnOpenRegisterForm.setOnClickListener(this);
    }

    public void loginUser(View view){

        if(mEditTxtUserPassword.getText().toString().trim()!="" &&
                mEditTxtUserEmail.getText().toString().trim()!=""){

        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    private void showErrorMsg(){
        journalApp.getDialogManager().showDialog(null,"Oops something went wrong, please try again later","");
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.btn_login_fb){
            fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                private ProfileTracker mProfileTracker;
                @Override
                public void onSuccess(LoginResult loginResult) {
                    if(Profile.getCurrentProfile() == null) {
                        mProfileTracker = new ProfileTracker() {
                            @Override
                            protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                                // profile2 is the new profile
                                profile2.getProfilePictureUri(200,200);

                                Log.d("DEBUG", profile2.getName());
                                Log.d("DEBUG", profile2.getProfilePictureUri(150,150).toString());
                                String userName = profile2.getName();
                                String userFirstName = profile2.getFirstName();
                                String userId = profile2.getId();
                                Uri userPhotori  = profile2.getProfilePictureUri(100,100);
                                mProfileTracker.stopTracking();
                            }
                        };
                    }
                    else {
                        Profile profile = Profile.getCurrentProfile();
                        Log.d("DEBUG", profile.getFirstName());
                    }
                }

                @Override
                public void onCancel() {
                    showErrorMsg();
                }

                @Override
                public void onError(FacebookException error) {
                    showErrorMsg();
                }
            });
        }else if(v.getId()==R.id.btn_open_login_form){
            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        }

    }
}
