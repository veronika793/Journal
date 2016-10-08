package com.veronica.medaily.fragments;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.veronica.medaily.Constants;
import com.veronica.medaily.MainApplication;
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
        mainTypeFace =Typeface.createFromAsset(getActivity().getAssets(), Constants.FONT_ONE);

    }

    protected void placeFragment( @IdRes int containerViewId,
                                @NonNull Fragment fragment,
                                @NonNull String fragmentTag) {

        getFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(null)
                .commit();
    }

    protected void setupTypefaceView(TextView view){
        view.setTypeface(mainTypeFace);
    }

    protected void setupUnderline(TextView view){
        view.setPaintFlags(view.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
    }
}
