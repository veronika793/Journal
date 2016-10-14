package com.veronica.medaily.loaders;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.veronica.medaily.adapters.CategoriesAdapter;
import com.veronica.medaily.dbmodels.Category;
import com.veronica.medaily.dbmodels.User;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Veronica on 10/10/2016.
 */
public class CategoriesLoader extends AsyncTask<Void,Integer,List<Category>> {

    private RecyclerView recyclerView;
    private CategoriesAdapter categoriesAdapter;
    private User user;
    private List<Category> categories;

    @Override
    protected List<Category> doInBackground(Void... params) {

        categories = user.getCategories();
        Collections.sort(categories, new Comparator<Category>() {
            @Override
            public int compare(Category lhs, Category rhs) {
                return lhs.getName().toLowerCase().compareTo(rhs.getName().toLowerCase());
            }
        });

        return categories;
    }

    public CategoriesLoader(User user, RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.user = user;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
//        progressBar.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(List<Category> cat) {
        super.onPostExecute(cat);
        categoriesAdapter = new CategoriesAdapter(categories);
        recyclerView.setAdapter(categoriesAdapter);
//        progressBar.setVisibility(View.GONE);
    }
}
