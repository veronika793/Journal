package com.veronica.journal.fragments;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.veronica.journal.Constants;
import com.veronica.journal.JournalApp;
import com.veronica.journal.R;
import com.veronica.journal.dbmodels.Note;
import com.veronica.journal.dbmodels.User;
import com.veronica.journal.helpers.BitmapHelper;
import com.veronica.journal.tasks.ImageLoader;


import java.io.FileNotFoundException;
import java.util.List;


public class HomeFragment extends Fragment {

    private TextView mTxtUser;
    private TextView mTxtJournal;
    private TextView mTxtNoNotesMsg;

    private ImageView mImgViewUserPhoto;

    JournalApp appJournal;
    private User mCurrentUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appJournal = (JournalApp)getActivity().getApplication();
        setCurrentUser();
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);


        mTxtUser = (TextView)view.findViewById(R.id.txt_user_name);
        mTxtNoNotesMsg = (TextView)view.findViewById(R.id.txt_no_notes_msg);
        mTxtNoNotesMsg.setPaintFlags(mTxtNoNotesMsg.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        mTxtJournal = (TextView)view.findViewById(R.id.txt_journal);
        mImgViewUserPhoto = (ImageView)view.findViewById(R.id.img_view_user_photo);
        mTxtUser.setText(mCurrentUser.getName());

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), Constants.FONT_ONE);
        mTxtUser.setTypeface(typeface);
        mTxtNoNotesMsg.setTypeface(typeface);
        mTxtJournal.setTypeface(typeface);
        try {
            Bitmap image = BitmapHelper.decodeUri(getActivity().getContentResolver(), Uri.parse(mCurrentUser.getPhotoUri()));
            Bitmap cropped = BitmapHelper.getCroppedBitmap(image);
            mImgViewUserPhoto.setImageBitmap(cropped);
        } catch (Exception e) {
            Log.d("DEBUG", e.getMessage());
        }
        return view;
    }


    private void placeFragment( @IdRes int containerViewId,
                                @NonNull Fragment fragment,
                                @NonNull String fragmentTag) {

        getFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(null)
                .commit();
    }

    private void setCurrentUser() {
        long userId = Long.parseLong(appJournal.getAuthManager().getUser());
        Long userId2 = Long.valueOf(appJournal.getAuthManager().getUser());
        mCurrentUser =  User.findById(User.class, Long.valueOf(userId2));
    }



}
