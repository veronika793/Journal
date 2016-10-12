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
public class NotesPreviewDialog extends Dialog{
        private Context context;
        private Note note;
        private TextView mNoteTitle;
        private TextView mNoteContent;
        private TextView mNoteCreatedOn;
        private TextView mNoteReminderDate;
        private Button mBtnClose;

        public NotesPreviewDialog(Context context, Note note) {
            super(context);
            this.note = note;
            this.context = context;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_category_preview);

//            txtCategoryTitle = (TextView) findViewById(R.id.txt_note_title_prev);
//            txtCategoryDescription = (TextView) findViewById(R.id.txt_note_content_prev);
//            btnClose = (Button) findViewById(R.id.dialog_btn_cat_close);
//
//            txtCategoryTitle.setText(category.getName());
//            txtCategoryDescription.setText(category.getDescription());
//
//            btnClose.setOnClickListener(this);

        }

//        @Override
//        public void onClick(View v) {
//            if(v.getId()==R.id.dialog_btn_cat_close){
//                this.dismiss();
//            }
//        }
}
