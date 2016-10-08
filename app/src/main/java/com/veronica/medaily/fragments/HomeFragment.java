package com.veronica.medaily.fragments;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.veronica.medaily.Constants;
import com.veronica.medaily.MainApplication;
import com.veronica.medaily.R;
import com.veronica.medaily.loaders.AvatarLoader;


public class HomeFragment extends BaseFragment {

    private TextView mTxtUser;
    private TextView mTxtWellcomeMsg;

    private ImageView mImgViewUserPhoto;

    ProgressBar progressBar;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        mTxtUser = (TextView)view.findViewById(R.id.txt_username);

        mTxtWellcomeMsg = (TextView)view.findViewById(R.id.text_wellcome_msg);
        mImgViewUserPhoto = (ImageView)view.findViewById(R.id.img_view_user_photo);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        super.setupUnderline(mTxtWellcomeMsg);
        super.setupTypefaceView(mTxtUser);
        super.setupTypefaceView(mTxtWellcomeMsg);
        //scrolling textview
        mTxtWellcomeMsg.setMovementMethod(new ScrollingMovementMethod());
        mTxtUser.setText("Hello "+mCurrentUser.getName());

        //parse profile image resize it make it circular and loads it asynchronously
        new AvatarLoader(getActivity(),mImgViewUserPhoto,progressBar).execute(Uri.parse(mCurrentUser.getPhotoUri()));
        return view;
    }


}
