package com.veronica.medaily.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.commonsware.cwac.colormixer.ColorMixer;
import com.veronica.medaily.Constants;
import com.veronica.medaily.R;
import com.veronica.medaily.bindingmodels.CategoryBindingModel;
import com.veronica.medaily.dbmodels.Category;
import com.veronica.medaily.helpers.NotificationHandler;

import java.util.InvalidPropertiesFormatException;

/**
 * Created by Veronica on 10/8/2016.
 */
public class AddCategoryFragment extends BaseFragment implements ColorMixer.OnColorChangedListener,View.OnClickListener {

    private NotificationHandler notificationHandler;

    private TextView mTextView;
    private EditText mEditTxtTitle;
    private EditText mEditTxtDescription;
    private ColorMixer mColorMixer;
    private Button mBtnAddCategory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.notificationHandler = new NotificationHandler(getContext());
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_category,container,false);

        mTextView = (TextView)view.findViewById(R.id.txt_view_add_category);
        mEditTxtDescription = (EditText)view.findViewById(R.id.edit_txt_category_description);
        mColorMixer = (ColorMixer)view.findViewById(R.id.mixer);
        mBtnAddCategory = (Button)view.findViewById(R.id.btn_create_category);
        mEditTxtTitle = (EditText)view.findViewById(R.id.edit_txt_category_title);

//        super.setupUnderline(mTextView);
        super.setupTypefaceView(mTextView);
        super.setupUnderline(mTextView);

        if(mBtnAddCategory!=null) {
            mBtnAddCategory.setOnClickListener(this);
        }
        //sets default color
        //TODO: must be changed
        mColorMixer.setColor(ContextCompat.getColor(getContext(),R.color.colorDefaultCategoryColor));
        mColorMixer.setOnColorChangedListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_create_category){

            long categoriesCount = Category.count(Category.class," user = ? ", new String[]{String.valueOf(mCurrentUser.getId())});
            if( categoriesCount < Constants.CATEGORIES_LIMIT_COUNT){
                String title = mEditTxtTitle.getText().toString().trim();
                String description = mEditTxtDescription.getText().toString().trim();
                try {
                    CategoryBindingModel categoryBindingModel = new CategoryBindingModel(title,description);
                    Category category = new Category(super.mCurrentUser,title,description,mColorMixer.getColor());
                    //check if this category exists for the current user if not creates
                    if(Category.find(Category.class," name = ? and user = ?",new String[]{title,String.valueOf(mCurrentUser.getId())}).isEmpty()) {
                        category.save();
                        notificationHandler.toastSuccessNotificationBottom("Category added");
                    }else{
                        notificationHandler.toastNeutralNotificationBottom("This category already exists");
                    }



                } catch (InvalidPropertiesFormatException e) {
                    notificationHandler.toastWarningNotificationBottom(e.getMessage());
                }


            }else{
                notificationHandler.toastNeutralNotificationBottom("You have reached you limit for categories");
            }

        }
    }

    @Override
    public void onColorChange(int i) {
        mColorMixer.setColor(i);
    }
}
