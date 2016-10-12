package com.veronica.medaily.adapters;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.veronica.medaily.Constants;
import com.veronica.medaily.R;
import com.veronica.medaily.dbmodels.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Veronica on 10/9/2016.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    private List<Category> mCategories = new ArrayList<>();
    private List<Category> mCategoriesCopy = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View categoryContainer;
        public TextView mCategoryName;
        public TextView mCategoryDescription;
        public TextView mTotalNotes;
        public ViewHolder(View v) {
            super(v);
            categoryContainer = v.findViewById(R.id.container_categories);
            mCategoryName = (TextView) v.findViewById(R.id.txt_category_name);
            Typeface typeface = Typeface.createFromAsset(v.getContext().getAssets(), Constants.FONT_ONE);
            mCategoryName.setTypeface(typeface);
//            mCategoryDescription = (TextView) v.findViewById(R.id.txt_category_description);
            mTotalNotes = (TextView)v.findViewById(R.id.txt_category_notes_count);

        }
    }

    public CategoriesAdapter(List<Category> categories) {
        this.mCategories = categories;
        this.mCategoriesCopy.addAll(categories);
    }

    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_categorie, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        int notesCount = mCategories.get(position).getNotes().size();
        String hexColor = String.format("#%06X", (0xFFFFFF & mCategories.get(position).getColor()));
        holder.categoryContainer.setBackgroundColor(Color.parseColor(hexColor));
        holder.mCategoryName.setText(mCategories.get(position).getName());
//        holder.mCategoryDescription.setText(mCategories.get(position).getDescription());
        holder.mTotalNotes.setText(String.valueOf(notesCount));

    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    public void filter(String text) {
        mCategories.clear();
        if(text.isEmpty()){
            mCategories.addAll(mCategoriesCopy);
        } else{
            text = text.toLowerCase();
            for (Category itemCopy : mCategoriesCopy) {
                if(itemCopy.getName().toLowerCase().contains(text) || itemCopy.getDescription().toLowerCase().contains(text)){
                    mCategories.add(itemCopy);
                }
            }
        }
        this.notifyDataSetChanged();
    }

    public void updateCategories(List<Category> categories){
        mCategories = categories;
        mCategoriesCopy.clear();
        mCategoriesCopy.addAll(categories);
        notifyDataSetChanged();
    }

    public void deleteCategory(Category category){
        this.mCategories.remove(category);
        mCategoriesCopy.remove(category);
        notifyDataSetChanged();
    }

    public List<Category> getCategories(){
        return mCategories;
    }
}
