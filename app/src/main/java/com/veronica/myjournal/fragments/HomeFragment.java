package com.veronica.myjournal.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.veronica.myjournal.Constants;
import com.veronica.myjournal.R;
import com.veronica.myjournal.app.MyJournalApplication;
import com.veronica.myjournal.models.Note;
import com.veronica.myjournal.models.User;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by Veronica on 10/3/2016.
 */
public class HomeFragment extends Fragment{

    private TextView mTxtUser;
    private TextView mTxtJournal;
    private TextView mTxtNoNotesMsg;

    private ImageView mImgViewUserPhoto;

    MyJournalApplication appJournal;
    private User mUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appJournal = (MyJournalApplication)getActivity().getApplication();
        if(getArguments().containsKey("current_user")) {
            mUser = getArguments().getParcelable("current_user");
        }
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        mTxtUser = (TextView)view.findViewById(R.id.txt_user_name);
        mTxtNoNotesMsg = (TextView)view.findViewById(R.id.txt_no_notes_msg);
        mTxtJournal = (TextView)view.findViewById(R.id.txt_journal);
        mImgViewUserPhoto = (ImageView)view.findViewById(R.id.img_view_user_photo);
        mTxtUser.setText(mUser.get_name());

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(),Constants.FONT_ONE);
        mTxtUser.setTypeface(typeface);
        mTxtNoNotesMsg.setTypeface(typeface);
        mTxtJournal.setTypeface(typeface);
        mImgViewUserPhoto.setImageBitmap(mUser.get_profilePic());

        List<Note> notes = appJournal.getNotesDbManager().getAllForUser(mUser.get_id());

        if(notes.size()==0){
            mTxtNoNotesMsg.setText(Constants.NO_NOTES_MSG);
            mTxtNoNotesMsg.setVisibility(View.VISIBLE);
        }else{
            mTxtNoNotesMsg.setVisibility(View.GONE);
            // load data in recyclerview
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


}
