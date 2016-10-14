package com.veronica.medaily.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.veronica.medaily.Constants;
import com.veronica.medaily.MainApplication;
import com.veronica.medaily.validationmodels.UserValidationModel;
import com.veronica.medaily.dbmodels.User;
import com.veronica.medaily.helpers.CipherHelper;
import com.veronica.medaily.helpers.NotificationHandler;
import com.veronica.medaily.R;
import com.veronica.medaily.helpers.KeyGenerator;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private MainApplication mainApp;
    private NotificationHandler notificationHandler;

    private EditText mEditTxtEmail;
    private EditText mEditTxtPassword;
    private EditText mEditTxtName;

    private Button mBtnSelectPhoto;
    private Button mBtnRegister;
    private Button mBtnOpenLoginForm;

    private String selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mainApp = (MainApplication) this.getApplication();
        if(mainApp.getAuthManager().isLoggedIn()){
            startActivity(new Intent(RegisterActivity.this,HomeActivity.class));
        }
        setContentView(R.layout.activity_register);

        notificationHandler = NotificationHandler.getInstance();

        mEditTxtEmail = (EditText) findViewById(R.id.edit_txt_reg_email);
        mEditTxtPassword = (EditText) findViewById(R.id.edit_txt_reg_password);
        mEditTxtName = (EditText) findViewById(R.id.edit_txt_reg_name);

        mBtnSelectPhoto = (Button) findViewById(R.id.btn_select_photo);
        mBtnRegister = (Button) findViewById(R.id.btn_register);
        mBtnOpenLoginForm = (Button) findViewById(R.id.btn_open_login_form);

        mBtnSelectPhoto.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
        mBtnOpenLoginForm.setOnClickListener(this);

    }

    public void selectUserPhoto(View view){
        openGallery();
    }

    private void openGallery() {

        Intent intent;
        if(Build.VERSION.SDK_INT>18){
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        }else{
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        }
        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        intent.setType("image/*");
        startActivityForResult(intent, Constants.PICK_PROFILE_IMAGE_REQ_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == Constants.PICK_PROFILE_IMAGE_REQ_CODE ) {
            mBtnSelectPhoto.setText(Constants.PHOTO_SELECTED);
            Uri selectedImage = data.getData();
            this.selectedImageUri = selectedImage.toString();
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_select_photo:openGallery();
                break;
            case R.id.btn_open_login_form: openLoginForm();
                break;
            case R.id.btn_register:

                String name = mEditTxtName.getText().toString().trim();
                String email = mEditTxtEmail.getText().toString().trim();
                String password = mEditTxtPassword.getText().toString();

                try {

                    String cypherKey = KeyGenerator.generateKey(email);
                    String passwordEncrypt = CipherHelper.cipher(cypherKey,password);
                    UserValidationModel userBindingModel = new UserValidationModel(email,password,name,selectedImageUri);
                    userBindingModel.set_password(passwordEncrypt);

                    if(!User.find(User.class,"email = ?",email).isEmpty()){
                        notificationHandler.toastWarningNotification("User already exists");
                    }else{
                        User newUser = new User(email,passwordEncrypt,name,selectedImageUri);
                        newUser.save();
                        mainApp.getAuthManager().loginUser(String.valueOf(newUser.getId()));
                    }

                } catch (Exception e) {
                    notificationHandler.toastWarningNotification(e.getMessage());
                }
                if(mainApp.getAuthManager().isLoggedIn()){
                    goToJournalActivity();
                }
                break;
        }

    }

    private void openLoginForm() {
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mainApp.getAuthManager().isLoggedIn()){
            startActivity(new Intent(RegisterActivity.this,HomeActivity.class));
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(mainApp.getAuthManager().isLoggedIn()){
            startActivity(new Intent(RegisterActivity.this,HomeActivity.class));
        }
    }

    private void goToJournalActivity(){
        startActivity(new Intent(RegisterActivity.this,HomeActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        // leaves  back stack as it is, just all activities in background
        moveTaskToBack(true);
    }

}
