package com.veronica.myjournal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.veronica.myjournal.R;
import com.veronica.myjournal.managers.LoginManager;

/**
 * Created by Veronica on 9/30/2016.
 */
public class ContainerFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_container,container,false);

        return view;
    }
}
