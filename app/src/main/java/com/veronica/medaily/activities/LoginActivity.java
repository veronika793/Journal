package com.veronica.medaily.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.veronica.medaily.MainApplication;
import com.veronica.medaily.helpers.NotificationHandler;
import com.veronica.medaily.R;
import com.veronica.medaily.bindingmodels.LoginUserBindingModel;
import com.veronica.medaily.dbmodels.User;
import com.veronica.medaily.helpers.CipherHelper;
import com.veronica.medaily.helpers.KeyGenerator;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private MainApplication mainApp;
    private NotificationHandler notificationHandler;

    //users login props
    private EditText mEditTxtUserEmail;
    private EditText mEditTxtUserPassword;
    private Button mBtnOpenRegisterForm;
    private Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainApp = (MainApplication) this.getApplication();

        if(mainApp.getAuthManager().isLoggedIn()){
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
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

                    List<User> userList = User.find(User.class,"email = ? and password = ?",new String[]{userEmail,encryptedPass});
                    if(userList.isEmpty()){
                        notificationHandler.toastWarningNotificationTop("Invalid username or password");
                    }else{
                        mainApp.getAuthManager().loginUser(String.valueOf(userList.get(0).getId()));
                    }

                } catch (Exception e) {
                    notificationHandler.toastWarningNotificationTop(e.getMessage());
                }

                if(mainApp.getAuthManager().isLoggedIn()){
                    goToJournalActivity();
                }
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(mainApp.getAuthManager().isLoggedIn()){
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mainApp.getAuthManager().isLoggedIn()){
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
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
