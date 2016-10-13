package com.veronica.medaily.fragments;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.veronica.medaily.Constants;
import com.veronica.medaily.MainApplication;
import com.veronica.medaily.R;
import com.veronica.medaily.dbmodels.User;

/**
 * Created by Veronica on 10/8/2016.
 */
public class BaseFragment extends Fragment {

    protected User mCurrentUser;
    protected MainApplication mainApp;
    protected Typeface mainTypeFace;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainApp = (MainApplication)getActivity().getApplication();
        Long userId = Long.valueOf(mainApp.getAuthManager().getUser());
        mCurrentUser = User.findById(User.class, userId);
        mainTypeFace = Typeface.createFromAsset(getActivity().getAssets(), Constants.FONT_ONE);

    }

    public void updateCurrentUser(){
        Long userId = Long.valueOf(mainApp.getAuthManager().getUser());
        mCurrentUser = User.findById(User.class,userId);
    }

    protected void placeFragment(@NonNull Fragment fragment) {
        String backStateName =  fragment.getClass().getName();
        String fragmentTag = backStateName;

        if(getActivity()!=null){
            FragmentManager manager = getActivity().getSupportFragmentManager();
            boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

            if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null){ //fragment not in back stack, create it.
                FragmentTransaction ft = manager.beginTransaction();
                ft.replace(R.id.content_frame, fragment, fragmentTag);
//            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(backStateName);
                ft.commit();
            }
        }

    }

    protected void setupTypefaceView(TextView view){
        view.setTypeface(mainTypeFace);
    }

    protected void setupUnderline(TextView view){
        view.setPaintFlags(view.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
    }
}
