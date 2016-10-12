package com.veronica.medaily.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.commonsware.cwac.colormixer.ColorMixer;
import com.veronica.medaily.R;
import com.veronica.medaily.dbmodels.Category;
import com.veronica.medaily.interfaces.ICategoryEditedListener;
import com.veronica.medaily.validationmodels.CategoryValidationModel;

import java.util.InvalidPropertiesFormatException;

/**
 * Created by Veronica on 10/11/2016.
 */
public class EditCategoryDialog extends Dialog implements View.OnClickListener,ColorMixer.OnColorChangedListener {
    private Context mContext;
    private Category mCategory;
    private ICategoryEditedListener listener;
    private int position;

    private EditText mEditTxtCategoryName;
    private EditText mEditTxtCategoryDescription;
    private Button mBtnEditCategory;
    private Button mBtnClose;
    private ColorMixer mColorMixer;

    public EditCategoryDialog(Context context, Category category, ICategoryEditedListener listener, int position) {
        super(context);
        this.mContext = context;
        this.mCategory = category;
        this.listener = listener;
        this.position = position;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_edit_category);

        mEditTxtCategoryName = (EditText) findViewById(R.id.txt_note_title_prev);
        mEditTxtCategoryDescription = (EditText)findViewById(R.id.txt_note_content_prev);
        mColorMixer = (ColorMixer)findViewById(R.id.mixer_edit_category);

        mEditTxtCategoryName.setText(mCategory.getName());
        mEditTxtCategoryDescription.setText(mCategory.getDescription());
        mColorMixer.setColor(mCategory.getColor());
        mColorMixer.setOnColorChangedListener(this);

        mBtnEditCategory = (Button)findViewById(R.id.btn_edit_category);
        mBtnClose = (Button) findViewById(R.id.btn_edit_cat_cancel);

        mBtnClose.setOnClickListener(this);
        mBtnEditCategory.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_edit_cat_cancel){
            this.dismiss();
        }else if(v.getId()==R.id.btn_edit_category){
            String title = mEditTxtCategoryName.getText().toString().trim();
            String description = mEditTxtCategoryDescription.getText().toString().trim();
            try {
                CategoryValidationModel categoryBindingModel = new CategoryValidationModel(title,description);
                mCategory.setName(title);
                mCategory.setDescription(description);
                mCategory.setColor(mColorMixer.getColor());
                mCategory.save();
                listener.categoryEdited(mCategory,position);
                this.dismiss();

            } catch (InvalidPropertiesFormatException e) {
                e.getMessage();
            }
        }
    }

    @Override
    public void onColorChange(int i) {
        mColorMixer.setColor(i);
    }
}
