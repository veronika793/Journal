package com.veronica.medaily.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.commonsware.cwac.colormixer.ColorMixer;
import com.veronica.medaily.R;

/**
 * Created by Veronica on 10/8/2016.
 */
public class AddCategoryFragment extends BaseFragment {

    private EditText mEditTxtTitle;
    private ColorMixer mColorMixer;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_category,container,false);

        mEditTxtTitle = (EditText)view.findViewById(R.id.edit_txt_category_title);
        mColorMixer = (ColorMixer)view.findViewById(R.id.mixer);
        mColorMixer.setColor(ContextCompat.getColor(getContext(),R.color.colorHomeBackground));


        mColorMixer.setOnColorChangedListener(new ColorMixer.OnColorChangedListener() {
            @Override
            public void onColorChange(int i) {
                mColorMixer.setColor(i);
            }
        });
        return view;
    }
}
