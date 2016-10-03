package com.veronica.myjournal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.veronica.myjournal.R;
import com.veronica.myjournal.app.MyJournalApplication;
import com.veronica.myjournal.helpers.Validator;
import com.veronica.myjournal.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private MyJournalApplication appJournal;

    //facebook login props
    private CallbackManager callbackManager = null;
    private AccessTokenTracker accessTokenTracker = null;
    private LoginButton fbLoginButton;

    //users login props
    private EditText mEditTxtUserEmail;
    private EditText mEditTxtUserPassword;
    private Button mBtnOpenRegisterForm;
    private Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        appJournal = (MyJournalApplication)this.getApplication();
        if(appJournal.getAuthorizationManager().isLoggedIn()){
            startActivity(new Intent(LoginActivity.this,JournalActivity.class));
            finish();
        }

        setContentView(R.layout.activity_login);

        //facebook login ..
        callbackManager = CallbackManager.Factory.create();
        fbLoginButton = (LoginButton) findViewById(R.id.btn_login_fb);
        fbLoginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email"));
        fbLoginButton.setOnClickListener(this);

        //users login..
        mEditTxtUserEmail = (EditText) findViewById(R.id.edit_txt_login_email);
        mEditTxtUserPassword = (EditText) findViewById(R.id.edit_txt_login_password);
        mBtnOpenRegisterForm = (Button) findViewById(R.id.btn_open_register_form);
        mBtnLogin = (Button) findViewById(R.id.btn_login);

        mBtnOpenRegisterForm.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }


    @Override
    public void onClick(View v) {

        //facebook login
        if(v.getId()==R.id.btn_login_fb){
            fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(final LoginResult loginResult) {
                    GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            Log.i("LoginActivity", response.toString());
                            try {
                                String userId = object.getString("id");
                                String userEmail = object.getString("email");
                                String userName = object.getString("first_name");
                                String userPicUri = "http://graph.facebook.com/"+userId+"/picture?width=150&height=150";

                                User userData = new User(null,userEmail,userName,userPicUri,true);
                                boolean userAlreadyExists = appJournal.getDbManager().checkIfUserExists(userEmail);
                                if(!userAlreadyExists){
                                    appJournal.getDbManager().addUser(userData);
                                }
                                appJournal.getAuthorizationManager().loginUser();
                                Toast.makeText(getApplicationContext(), "Fb user logged in", Toast.LENGTH_SHORT).show();

                                if(appJournal.getAuthorizationManager().isLoggedIn()){
                                    startActivity(new Intent(LoginActivity.this,JournalActivity.class));
                                    finish();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    request.executeAsync();

                }
                @Override
                public void onCancel() {
                    Toast.makeText(getApplicationContext(), "Something went wrong with the login", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(FacebookException error) {
                    Toast.makeText(getApplicationContext(), "User logged in", Toast.LENGTH_SHORT).show();
                }
            });

        }else if(v.getId()==R.id.btn_open_register_form){
            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        }else if(v.getId()==R.id.btn_login){
            String userEmail = mEditTxtUserEmail.getText().toString();
            String userPassword = mEditTxtUserPassword.getText().toString();
            if(Validator.validateLoginUserFields(userEmail,userPassword)){

                    if(appJournal.getDbManager().checkIfUserExist(userEmail,userPassword)){
                        appJournal.getAuthorizationManager().loginUser();
                        Toast.makeText(getApplicationContext(), "User logged in", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
            }else{
                Toast.makeText(getApplicationContext(),"Invalid input" , Toast.LENGTH_SHORT).show();
            }
            if(appJournal.getAuthorizationManager().isLoggedIn()){
                startActivity(new Intent(LoginActivity.this,JournalActivity.class));
                finish();
            }

        }

        if(appJournal.getAuthorizationManager().isLoggedIn()){
            startActivity(new Intent(LoginActivity.this,JournalActivity.class));
            finish();
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(appJournal.getAuthorizationManager().isLoggedIn()){
            startActivity(new Intent(LoginActivity.this,JournalActivity.class));
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(appJournal.getAuthorizationManager().isLoggedIn()){
            startActivity(new Intent(LoginActivity.this,JournalActivity.class));
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
