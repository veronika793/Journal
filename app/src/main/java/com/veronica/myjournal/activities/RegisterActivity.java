package com.veronica.myjournal.activities;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.veronica.myjournal.Constants;
import com.veronica.myjournal.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText mEditTxtUsername;
    private EditText mEditTxtEmail;
    private EditText mEditTxtPassword;
    private EditText mEditTxtName;
    private Uri imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEditTxtUsername = (EditText) findViewById(R.id.edit_txt_reg_username);
        mEditTxtEmail = (EditText) findViewById(R.id.edit_txt_reg_email);
        mEditTxtPassword = (EditText) findViewById(R.id.edit_txt_reg_password);
        mEditTxtName = (EditText) findViewById(R.id.edit_txt_reg_name);
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
            imageUrl = data.getData();
        }
    }

    public void registerUser(View view){

    }

    public void openLoginForm(View view){
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
    }
}
