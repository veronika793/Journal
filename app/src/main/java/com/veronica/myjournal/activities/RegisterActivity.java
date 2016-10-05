package com.veronica.myjournal.activities;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.veronica.myjournal.Constants;
import com.veronica.myjournal.R;
import com.veronica.myjournal.app.MyJournalApplication;
import com.veronica.myjournal.bindingmodels.UserBindingModel;
import com.veronica.myjournal.helpers.CipherHelper;
import com.veronica.myjournal.helpers.ErrorHandler;
import com.veronica.myjournal.helpers.InputValidator;
import com.veronica.myjournal.helpers.KeyGenerator;
import com.veronica.myjournal.helpers.NotificationHandler;

import java.util.InvalidPropertiesFormatException;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private MyJournalApplication appJournal;

    ErrorHandler errorHandler;
    InputValidator inputValidator;
    NotificationHandler notificationHandler;

    private EditText mEditTxtEmail;
    private EditText mEditTxtPassword;
    private EditText mEditTxtName;
    private String imageUrl;

    private Button mBtnSelectPhoto;
    private Button mBtnRegister;
    private Button mBtnOpenLoginForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.appJournal = (MyJournalApplication) this.getApplication();
        if(appJournal.getAuthManager().isLoggedIn()){
            startActivity(new Intent(RegisterActivity.this,JournalActivity.class));
        }
        setContentView(R.layout.activity_register);

        errorHandler = new ErrorHandler();
        inputValidator = new InputValidator();
        notificationHandler = new NotificationHandler(this);

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
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, Constants.PICK_IMAGE_REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK && requestCode == Constants.PICK_IMAGE_REQ_CODE ){
            imageUrl = data.getData().toString();
            mBtnSelectPhoto.setText(imageUrl);
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

                String name = mEditTxtName.getText().toString();
                String email = mEditTxtEmail.getText().toString();
                String password = mEditTxtPassword.getText().toString();
                //TODO : only for debug purposes - must be changed
                //String photoUri = mBtnSelectPhoto.getText().toString();
                String photoUri = Constants.TEST_PHOTO_URL;

                try {
                    if(appJournal.getUserDbManager().checkIfExists(email)){
                        notificationHandler.toastWarningNotificationTop("User already exists");
                    }else{
                        String cypherKey = KeyGenerator.generateKey(email);
                        Log.d("DEBUG", String.valueOf(cypherKey.length()));
                        String passwordEncrypt = CipherHelper.cipher(cypherKey,password);
                        UserBindingModel userBindingModel = new UserBindingModel(email,password,name,photoUri);
                        userBindingModel.set_password(passwordEncrypt);
                        appJournal.getUserDbManager().insert(userBindingModel);
                        appJournal.getAuthManager().loginUser(email);
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

    private void openLoginForm() {
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(appJournal.getAuthManager().isLoggedIn()){
            startActivity(new Intent(RegisterActivity.this,JournalActivity.class));
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(appJournal.getAuthManager().isLoggedIn()){
            startActivity(new Intent(RegisterActivity.this,JournalActivity.class));
        }
    }

    private void goToJournalActivity(){
        startActivity(new Intent(RegisterActivity.this,JournalActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        // leaves  back stack as it is, just all activities in background
        moveTaskToBack(true);
    }

}
