package com.veronica.medaily.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.veronica.medaily.R;
import com.veronica.medaily.dbmodels.Note;

/**
 * Created by Veronica on 10/13/2016.
 */
public class NotesPreviewDialog extends Dialog implements View.OnClickListener{
        private Note note;
        private TextView mNoteTitle;
        private TextView mNoteContent;
        private TextView mNoteCreatedOn;
        private TextView mNoteReminderDate;
        private Button mBtnClose;

        public NotesPreviewDialog(Context context, Note note) {
            super(context);
            this.note = note;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_note_preview);

            mNoteTitle = (TextView) findViewById(R.id.txt_note_title_prev);
            mNoteContent = (TextView) findViewById(R.id.txt_note_content_prev);
            mNoteCreatedOn = (TextView)findViewById(R.id.txt_note_created_prev);
            mNoteReminderDate = (TextView)findViewById(R.id.txt_note_remind_prev);

            mBtnClose = (Button) findViewById(R.id.dialog_btn_note_close);

            mNoteTitle.setText(note.getTitle());
            mNoteContent.setText(note.getContent());
            mNoteCreatedOn.setText(note.getCreatedOnDate());
            if(note.getReminderDate()==null){
                mNoteReminderDate.setText("No reminder");
            }else{
                mNoteReminderDate.setText(note.getReminderDate());
            }

            mBtnClose.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.dialog_btn_note_close){
                this.dismiss();
            }
        }
}
