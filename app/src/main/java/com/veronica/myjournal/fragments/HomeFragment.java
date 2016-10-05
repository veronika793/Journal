package com.veronica.myjournal.fragments;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.veronica.myjournal.Constants;
import com.veronica.myjournal.R;
import com.veronica.myjournal.models.User;

/**
 * Created by Veronica on 10/3/2016.
 */
public class HomeFragment extends Fragment{

    private Button btnGoToEdit;
    private User mUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments().containsKey("current_user")) {
            mUser = getArguments().getParcelable("current_user");
        }
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);


        btnGoToEdit = (Button) view.findViewById(R.id.btn_go_to_edit);
        btnGoToEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeFragment(R.id.content_frame,new AddNoteFragment(),"edit_fragment");
            }
        });




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
