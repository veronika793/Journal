package com.veronica.medaily.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.veronica.medaily.R;
import com.veronica.medaily.dbmodels.Category;

/**
 * Created by Veronica on 10/11/2016.
 */
public class CategoriesDetailsDialog extends Dialog implements View.OnClickListener{

    private Context context;
    private Category category;
    private TextView txtCategoryTitle;
    private TextView txtCategoryDescription;
    private Button btnClose;

    public CategoriesDetailsDialog(Context context, Category category) {
        super(context);
        this.category = category;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_category_details);

        txtCategoryTitle = (TextView) findViewById(R.id.edit_txt_edit_cat_name);
        txtCategoryDescription = (TextView) findViewById(R.id.edit_txt_edit_cat_descr);
        btnClose = (Button) findViewById(R.id.dialog_btn_cat_close);

        txtCategoryTitle.setText(category.getName());
        txtCategoryDescription.setText(category.getDescription());

        btnClose.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.dialog_btn_cat_close){
            this.dismiss();
        }
    }
}
