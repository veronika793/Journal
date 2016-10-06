package com.veronica.journal.fragments;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.veronica.journal.Constants;
import com.veronica.journal.JournalApp;
import com.veronica.journal.R;
import com.veronica.journal.dbmodels.User;
import com.veronica.journal.loaders.AvatarLoader;


public class HomeFragment extends Fragment {

    private TextView mTxtUser;
    private TextView mTxtJournal;
    private TextView mTxtNoNotesMsg;

    private ImageView mImgViewUserPhoto;

    ProgressBar progressBar;

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
        mTxtJournal = (TextView)view.findViewById(R.id.txt_journal);
        mImgViewUserPhoto = (ImageView)view.findViewById(R.id.img_view_user_photo);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        mTxtNoNotesMsg.setPaintFlags(mTxtNoNotesMsg.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        mTxtUser.setText(mCurrentUser.getName());

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), Constants.FONT_ONE);
        mTxtUser.setTypeface(typeface);
        mTxtNoNotesMsg.setTypeface(typeface);
        mTxtJournal.setTypeface(typeface);

        new AvatarLoader(getActivity(),mImgViewUserPhoto,progressBar).execute(Uri.parse(mCurrentUser.getPhotoUri()));

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
        mCurrentUser =  User.findById(User.class, userId);
    }



}
