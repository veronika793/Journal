package com.veronica.medaily.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.veronica.medaily.R;
import com.veronica.medaily.dbmodels.Note;
import com.veronica.medaily.interfaces.INoteEditedListener;
import com.veronica.medaily.validationmodels.NoteValidationModel;

import java.util.InvalidPropertiesFormatException;

/**
 * Created by Veronica on 10/11/2016.
 */
public class EditNoteDialog extends Dialog implements View.OnClickListener{
    private Note mNote;
    private INoteEditedListener mListener;

    private EditText mEditTxtNoteTitle;
    private EditText mEditTxtNoteContent;

    private int position;

    private Button mBtnEdit;
    private Button mBtnCancel;

    public EditNoteDialog(Context context, Note note,int position, INoteEditedListener listener) {
        super(context);
        this.mNote = note;
        this.mListener = listener;
        this.position = position;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_edit_note);

        mEditTxtNoteTitle = (EditText) findViewById(R.id.edit_txt_note_edit);
        mEditTxtNoteContent = (EditText) findViewById(R.id.edit_txt_note_content_edit);

        mEditTxtNoteTitle.setText(mNote.getTitle());
        mEditTxtNoteContent.setText(mNote.getContent());

        mBtnEdit = (Button) findViewById(R.id.btn_edit_note);
        mBtnCancel = (Button)findViewById(R.id.btn_cancel_edit_note);
        mBtnEdit.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_cancel_edit_note){
            this.dismiss();
        }else if(v.getId()==R.id.btn_edit_note){
            String title = mEditTxtNoteTitle.getText().toString().trim();
            String content = mEditTxtNoteContent.getText().toString().trim();
            try {
                NoteValidationModel noteValidationModel = new NoteValidationModel(title,content,mNote.getCategory().getName());
                mNote.setTitle(title);
                mNote.setContent(content);
                mNote.save();
                mListener.noteEdited(mNote,position);
                this.dismiss();

            } catch (InvalidPropertiesFormatException e) {
                e.getMessage();
            }
        }
    }

}
