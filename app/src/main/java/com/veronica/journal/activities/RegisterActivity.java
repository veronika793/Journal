package com.veronica.journal.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.orm.SugarRecord;
import com.veronica.journal.Constants;
import com.veronica.journal.JournalApp;
import com.veronica.journal.R;
import com.veronica.journal.bindingmodels.UserBindingModel;
import com.veronica.journal.dbmodels.User;
import com.veronica.journal.helpers.BitmapHelper;
import com.veronica.journal.helpers.CipherHelper;
import com.veronica.journal.helpers.InputValidator;
import com.veronica.journal.helpers.KeyGenerator;
import com.veronica.journal.helpers.NotificationHandler;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private JournalApp appJournal;

    InputValidator inputValidator;
    NotificationHandler notificationHandler;

    private EditText mEditTxtEmail;
    private EditText mEditTxtPassword;
    private EditText mEditTxtName;
    private String imageUrl;

    private Button mBtnSelectPhoto;
    private Button mBtnRegister;
    private Button mBtnOpenLoginForm;

    private String selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.appJournal = (JournalApp) this.getApplication();
        if(appJournal.getAuthManager().isLoggedIn()){
            startActivity(new Intent(RegisterActivity.this,JournalActivity.class));
        }
        setContentView(R.layout.activity_register);

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
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("image/*");
        startActivityForResult(intent, Constants.PICK_IMAGE_REQ_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }
        else if (requestCode == Constants.PICK_IMAGE_REQ_CODE) {
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

                String name = mEditTxtName.getText().toString();
                String email = mEditTxtEmail.getText().toString();
                String password = mEditTxtPassword.getText().toString();

                try {

                    String cypherKey = KeyGenerator.generateKey(email);
                    String passwordEncrypt = CipherHelper.cipher(cypherKey,password);
                    UserBindingModel userBindingModel = new UserBindingModel(email,password,name,selectedImageUri);
                    userBindingModel.set_password(passwordEncrypt);

                    if(!User.find(User.class,"email = ?",email).isEmpty()){
                        notificationHandler.toastWarningNotificationTop("User already exists");
                    }else{
                        User newUser = new User(email,passwordEncrypt,name,selectedImageUri);
                        newUser.save();
                        appJournal.getAuthManager().loginUser(String.valueOf(newUser.getId()));
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
