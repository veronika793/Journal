package com.veronica.myjournal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import com.veronica.myjournal.bindingmodels.LoginUserBindingModel;
import com.veronica.myjournal.bindingmodels.UserBindingModel;
import com.veronica.myjournal.helpers.CipherHelper;
import com.veronica.myjournal.helpers.InputValidator;
import com.veronica.myjournal.helpers.KeyGenerator;
import com.veronica.myjournal.helpers.NotificationHandler;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.InvalidPropertiesFormatException;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private MyJournalApplication appJournal;
    private NotificationHandler notificationHandler;

    //users login props
    private EditText mEditTxtUserEmail;
    private EditText mEditTxtUserPassword;
    private Button mBtnOpenRegisterForm;
    private Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appJournal = (MyJournalApplication)this.getApplication();
        if(appJournal.getAuthManager().isLoggedIn()){
            startActivity(new Intent(LoginActivity.this,JournalActivity.class));
            finish();
        }
        setContentView(R.layout.activity_login);

        notificationHandler = new NotificationHandler(this);

        mEditTxtUserEmail = (EditText) findViewById(R.id.edit_txt_login_email);
        mEditTxtUserPassword = (EditText) findViewById(R.id.edit_txt_login_password);
        mBtnOpenRegisterForm = (Button) findViewById(R.id.btn_open_register_form);
        mBtnLogin = (Button) findViewById(R.id.btn_login);

        mBtnOpenRegisterForm.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_open_register_form: openRegisterForm();
                break;
            case R.id.btn_login:

                String userEmail = mEditTxtUserEmail.getText().toString();
                String userPassword = mEditTxtUserPassword.getText().toString();

                try {

                    // generates custom key for each user

                    String cypherKey = KeyGenerator.generateKey(userEmail);
                    String encryptedPass = CipherHelper.cipher(cypherKey,userPassword);
                    LoginUserBindingModel loginUserBinding = new LoginUserBindingModel(userEmail,userPassword);
                    if(appJournal.getUserDbManager().isLoginValid(userEmail,encryptedPass)){
                        appJournal.getAuthManager().loginUser(userEmail);
                    }else{
                        notificationHandler.toastWarningNotificationTop("Invalid username or password");
                    }

                } catch (Exception e) {
                    notificationHandler.toastWarningNotificationTop(e.getMessage());
                }

                if(appJournal.getAuthManager().isLoggedIn()){
                    goToJournalActivity();
                }
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(appJournal.getAuthManager().isLoggedIn()){
            startActivity(new Intent(LoginActivity.this,JournalActivity.class));
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(appJournal.getAuthManager().isLoggedIn()){
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
