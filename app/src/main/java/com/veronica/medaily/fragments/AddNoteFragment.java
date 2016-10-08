package com.veronica.medaily.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.veronica.medaily.Constants;
import com.veronica.medaily.R;


/**
 * Created by Veronica on 9/30/2016.
 */
public class AddNoteFragment extends BaseFragment {

    private EditText mEditTxtTitle;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_note,container,false);

//        mEditT    xtTitle = (EditText) view.findViewById(R.id.edit_txt_note_title);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), Constants.FONT_ONE);
        //mEditTxtTitle.setTypeface(typeface);

        return view;
    }


    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 460);
        intent.putExtra("outputY", 460);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
