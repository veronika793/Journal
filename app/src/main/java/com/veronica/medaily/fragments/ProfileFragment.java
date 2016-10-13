package com.veronica.medaily.fragments;

import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.veronica.medaily.Constants;
import com.veronica.medaily.R;
import com.veronica.medaily.dbmodels.User;
import com.veronica.medaily.helpers.CipherHelper;
import com.veronica.medaily.helpers.InputValidator;
import com.veronica.medaily.helpers.KeyGenerator;
import com.veronica.medaily.helpers.NotificationHandler;
import com.veronica.medaily.loaders.AvatarLoader;
import com.veronica.medaily.validationmodels.UserValidationModel;

/**
 * Created by Veronica on 10/3/2016.
 */
public class ProfileFragment extends BaseFragment implements View.OnClickListener{

    NotificationHandler notificationManager;
    private ImageView mUserPhotoPreview;
    private EditText mUserName;
    private EditText mUserPassword;
    private Button mBtnSelectPhoto;
    private Button mBtnSaveChanges;
    private String photoUri = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificationManager = new NotificationHandler(getContext());
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);

        mUserPhotoPreview = (ImageView) view.findViewById(R.id.img_user_photo_preview);
        mUserName = (EditText)view.findViewById(R.id.edit_txt_name_new);
        mUserPassword = (EditText)view.findViewById(R.id.edit_txt_password_new);

        mBtnSaveChanges = (Button)view.findViewById(R.id.btn_save_new_profile);
        mBtnSelectPhoto = (Button)view.findViewById(R.id.btn_select_photo_edit);

        if(mBtnSelectPhoto!=null) {
            mBtnSelectPhoto.setOnClickListener(this);
        }
        if(mBtnSaveChanges!=null) {
            mBtnSaveChanges.setOnClickListener(this);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        boolean isPasswordEdited = false;
        boolean isNameEdited = false;
        boolean isPhotoEdited = false;

        if(v.getId()==R.id.btn_save_new_profile){
            String name = mUserName.getText().toString().trim();
            String password = mUserPassword.getText().toString();

            if(InputValidator.isMinLenghRestricted(Constants.NAME_MIN_LENGTH,name)){
                mCurrentUser.setName(name);
                isNameEdited = true;
            }
            if(InputValidator.isMinLenghRestricted(Constants.PASSWORD_MIN_LENGTH,password)){
                String cypherKey = KeyGenerator.generateKey(mCurrentUser.getEmail());
                try {
                    String passwordEncrypt = CipherHelper.cipher(cypherKey,password);
                    mCurrentUser.setPassword(passwordEncrypt);
                    isPasswordEdited = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    isPasswordEdited = false;
                }
            }
            if(InputValidator.isValidUri(photoUri)){
                mCurrentUser.setPhotoUri(photoUri);
                isPhotoEdited = true;
            }
            if(isNameEdited || isPasswordEdited || isPhotoEdited) {
                mCurrentUser.save();
                notificationManager.toastNeutralNotificationBottom("Profile edited successfully");
            }else{
                notificationManager.toastNeutralNotificationBottom("Invalid input fields");
            }

        }else if(v.getId()==R.id.btn_select_photo_edit){
            openGallery();

        }
    }

    private void openGallery() {
        Intent intent;
        if(Build.VERSION.SDK_INT>18){
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        }else{
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        }
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, Constants.PICK_PROFILE_IMAGE_REQ_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && requestCode == Constants.PICK_PROFILE_IMAGE_REQ_CODE ) {
            mBtnSelectPhoto.setText(Constants.PHOTO_SELECTED);
            Uri selectedImage = data.getData();
            this.photoUri = selectedImage.toString();
            new AvatarLoader(getActivity(),mUserPhotoPreview,null).execute(Uri.parse(photoUri));
        }
    }
}