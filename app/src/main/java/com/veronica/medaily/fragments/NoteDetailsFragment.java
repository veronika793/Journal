package com.veronica.medaily.fragments;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.veronica.medaily.R;
import com.veronica.medaily.dbmodels.Category;
import com.veronica.medaily.dbmodels.Note;
import com.veronica.medaily.loaders.AvatarLoader;
import com.veronica.medaily.loaders.NoteImgLoader;

/**
 * Created by Veronica on 10/11/2016.
 */
public class NoteDetailsFragment extends BaseFragment {

    private Note note;
    private View mContainer;
    private View mTextContainer;
    private TextView mNoteTitle;
    private TextView mNoteContent;
    private TextView mNoteCategory;
    private ImageView mNotePhoto;
    private TextView mNoteCreatedOn;
    private TextView mNoteReminderDate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if(bundle!=null){
            long noteId = bundle.getLong("note_id");
            note = Note.findById(Note.class,noteId);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_details,container,false);

        mNoteTitle = (TextView) view.findViewById(R.id.txt_note_detaisl_title);
        mNoteContent = (TextView)view.findViewById(R.id.txt_note_details_contnt);
        mNoteCategory = (TextView) view.findViewById(R.id.txt_note_details_categ);
        mNotePhoto = (ImageView) view.findViewById(R.id.txt_note_details_img);
        mNoteCreatedOn = (TextView) view.findViewById(R.id.txt_note_details_created);
        mNoteReminderDate = (TextView) view.findViewById(R.id.txt_note_details_remind);
        mContainer = view.findViewById(R.id.main_note_details_container);
        mTextContainer = view.findViewById(R.id.bottom_note_container);

        if(note!=null) {
            String hexColor = String.format("#%06X", (0xFFFFFF & note.getCategory().getColor()));
            mContainer.setBackgroundColor(Color.parseColor(hexColor));
            mTextContainer.setBackgroundColor(Color.parseColor(hexColor));
            mNoteTitle.setText(note.getTitle());
            mNoteContent.setText(note.getContent());
            mNoteCategory.setText(getString(R.string.note_categ_placehold,note.getCategory().getName()));
            mNoteCreatedOn.setText(getString(R.string.note_creaded_on_placeh,note.getCreatedOnDate()));
            if(note.getPhotoUri()!=null){
                mNotePhoto.setVisibility(View.VISIBLE);
                new NoteImgLoader(getActivity(),mNotePhoto).execute(Uri.parse(note.getPhotoUri()));
            }
            if(note.getReminderDate()==null){
                mNoteReminderDate.setText(getString(R.string.note_remind_date_placeh,"No reminder"));
            }else{
                mNoteReminderDate.setText(getString(R.string.note_remind_date_placeh,note.getReminderDate()));
            }

            super.setupTypefaceView(mNoteTitle);
            super.setupTypefaceView(mNoteContent);
            super.setupTypefaceView(mNoteCategory);
            super.setupTypefaceView(mNoteCreatedOn);
            super.setupTypefaceView(mNoteReminderDate);
            super.setupUnderline(mNoteTitle);
            mNoteContent.setMovementMethod(new ScrollingMovementMethod());
        }

        return view;
    }
}
