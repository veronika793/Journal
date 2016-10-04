package com.veronica.myjournal.activities;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.veronica.myjournal.Constants;
import com.veronica.myjournal.R;
import com.veronica.myjournal.app.MyJournalApplication;
import com.veronica.myjournal.helpers.ErrorHandler;
import com.veronica.myjournal.helpers.InputValidator;
import com.veronica.myjournal.helpers.NotificationHandler;
import com.veronica.myjournal.models.User;

import se.simbio.encryption.Encryption;

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
        if(appJournal.getAuthorizationManager().isLoggedIn()){
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
            case R.id.btn_open_login_form:goToJournalActivity();
                break;
            case R.id.btn_register:

                String name = mEditTxtName.getText().toString();
                String email = mEditTxtEmail.getText().toString();
                String password = mEditTxtPassword.getText().toString();
                //TODO : only for debug purposes - must be changed
                //String photoUri = mBtnSelectPhoto.getText().toString();
                String photoUri = Constants.TEST_PHOTO_URL;

                Encryption encryption = Encryption.getDefault("Key", email, new byte[16]);
                String encryptedPassword = encryption.encryptOrNull(password);

                if(!inputValidator.isValidUri(photoUri)){
                    notificationHandler.toastWarningNotification("Invalid photo uri");
                }else if(!inputValidator.isValidEmail(email)){
                    notificationHandler.toastWarningNotification("Invalid email");
                }else if(!inputValidator.isMinLenghRestricted(Constants.NAME_MIN_LENGHT,name)){
                    notificationHandler.toastWarningNotification("Invalid name. Minimum "+ Constants.NAME_MIN_LENGHT + " symbols");
                }else if(!inputValidator.isMinLenghRestricted(Constants.PASSWORD_MIN_LENGHT,password)){
                    notificationHandler.toastWarningNotification("Invalid password. Minimum "+ Constants.NAME_MIN_LENGHT + " symbols");
                }else{
                    notificationHandler.toastNotification("Valid");

                    if(appJournal.getDbManager().checkIfUserExists(email)){
                        notificationHandler.toastWarningNotification("User already exists");
                    }else{
                        User user = new User(null,email,encryptedPassword,name,photoUri,false);
                        appJournal.getDbManager().addUser(user);
                        appJournal.getAuthorizationManager().loginUser();
                    }

                    if(appJournal.getAuthorizationManager().isLoggedIn()){
                        goToJournalActivity();
                    }
                }

                break;
        }

    }


    @Override
    public void onBackPressed() {
        // leaves  back stack as it is, just all activities in background
        moveTaskToBack(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(appJournal.getAuthorizationManager().isLoggedIn()){
            startActivity(new Intent(RegisterActivity.this,JournalActivity.class));
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(appJournal.getAuthorizationManager().isLoggedIn()){
            startActivity(new Intent(RegisterActivity.this,JournalActivity.class));
        }
    }

    private void goToJournalActivity(){
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        finish();
    }


}
