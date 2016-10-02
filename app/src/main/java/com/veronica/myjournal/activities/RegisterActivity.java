package com.veronica.myjournal.activities;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.veronica.myjournal.Constants;
import com.veronica.myjournal.R;
import com.veronica.myjournal.models.User;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

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
        setContentView(R.layout.activity_register);

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
            mBtnSelectPhoto.setText(Constants.PHOTO_SELECTED);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_select_photo){
            openGallery();
        }else if(v.getId()==R.id.btn_register){
            // check if all fields are not empty and add user to database..
            //login user..

        }else if(v.getId()==R.id.btn_open_login_form){
            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        }
    }
}
