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
import com.veronica.myjournal.Constants;
import com.veronica.myjournal.R;
import com.veronica.myjournal.app.MyJournalApplication;
import com.veronica.myjournal.helpers.InputValidator;
import com.veronica.myjournal.helpers.NotificationHandler;
import com.veronica.myjournal.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import se.simbio.encryption.Encryption;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private MyJournalApplication appJournal;
    private NotificationHandler notificationHandler;
    private InputValidator inputValidator;

    //facebook login props
    private CallbackManager callbackManager = null;
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

        inputValidator = new InputValidator();
        notificationHandler = new NotificationHandler(this);

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

        switch (v.getId()){
            case R.id.btn_login_fb:
                fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    String id = object.getString("id");
                                    String email = object.getString("email");
                                    String name = object.getString("first_name");
                                    String picUri = "http://graph.facebook.com/"+id+"/picture?width=150&height=150";

                                    User userData = new User(null,email,name,picUri,true);

                                    if(!appJournal.getDbManager().checkIfUserExists(email)){
                                        appJournal.getDbManager().addUser(userData);
                                    }
                                    appJournal.getAuthorizationManager().loginUser();
                                    if(appJournal.getAuthorizationManager().isLoggedIn()){
                                        goToJournalActivity();
                                    }

                                } catch (JSONException e) {
                                    notificationHandler.toastWarningNotification(e.getMessage());
                                }
                            }
                        });
                        request.executeAsync();

                    }
                    @Override
                    public void onCancel() {
                        notificationHandler.toastWarningNotification("Couldn't log in");                    }

                    @Override
                    public void onError(FacebookException error) {
                        notificationHandler.toastWarningNotification("Couldn't log in");
                    }
                });

                break;
            case R.id.btn_open_register_form: openRegisterForm();
                break;
            case R.id.btn_login:

                String userEmail = mEditTxtUserEmail.getText().toString();
                String userPassword = mEditTxtUserPassword.getText().toString();

                if(!inputValidator.isValidEmail(userEmail)){
                    notificationHandler.toastWarningNotification("Invalid email");
                }else if(!inputValidator.isMinLenghRestricted(Constants.PASSWORD_MIN_LENGHT,userPassword)){
                    notificationHandler.toastWarningNotification("Invalid password. Minimum "+ Constants.NAME_MIN_LENGHT + " symbols");
                }else{
                    Encryption encryption = Encryption.getDefault("Key", userEmail, new byte[16]);
                    String passwordDecrypted = encryption.encryptOrNull(userPassword);
                    if(appJournal.getDbManager().checkIfUserExist(userEmail,passwordDecrypted)){
                        appJournal.getAuthorizationManager().loginUser();
                    }else{
                        notificationHandler.toastWarningNotification("Invalid username or password");
                    }
                }
                if(appJournal.getAuthorizationManager().isLoggedIn()){
                    goToJournalActivity();
                }
                break;
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
    private void openRegisterForm(){
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        finish();
    }

    private void goToJournalActivity(){
        startActivity(new Intent(LoginActivity.this,LoginActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
