package com.veronica.medaily.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.veronica.medaily.R;
import com.veronica.medaily.RecyclerClickListener;
import com.veronica.medaily.adapters.CategoriesAdapter;
import com.veronica.medaily.dbmodels.Category;
import com.veronica.medaily.dbmodels.Note;
import com.veronica.medaily.dbmodels.NoteReminder;
import com.veronica.medaily.dialogs.CategoriesDetailsDialog;
import com.veronica.medaily.dialogs.EditCategoryDialog;
import com.veronica.medaily.interfaces.ICategoryEditListener;
import com.veronica.medaily.tasks.CategoriesLoader;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class CategoriesFragment extends BaseFragment implements android.widget.SearchView.OnQueryTextListener,ICategoryEditListener {

    private SearchView mSearchViewCategories;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar progressBar;
    private boolean singleClick = false;
    private boolean doubleClick = false;
    private CategoriesFragment categoriesFragment;

    private List<Category> userCategories;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoriesFragment = this;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories,container,false);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar_categories);
        mSearchViewCategories = (SearchView) view.findViewById(R.id.search_view_categories);
        mSearchViewCategories.setOnQueryTextListener(this);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_categories);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.addOnItemTouchListener(
                new RecyclerClickListener(getContext(), mRecyclerView ,new RecyclerClickListener.OnItemClickListener() {

                    @Override public void onItemClick(View view, int position) {
                        Log.d("DEBUG", "item clicked");
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        EditCategoryDialog editCategoryDialog = new EditCategoryDialog(getContext(),userCategories.get(position),categoriesFragment,position);
                        editCategoryDialog.show();
                    }

                    @Override
                    public void onDoubleTab(View view, int position) {
                        CategoriesDetailsDialog detailsDialog = new CategoriesDetailsDialog(getContext(),userCategories.get(position));
                        detailsDialog.show();
                    }
                })
        );

        try {
            userCategories = new CategoriesLoader(progressBar,mCurrentUser,mRecyclerView).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return view;
    }

    ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(

            new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                    ItemTouchHelper.RIGHT) {

                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    int elementPosition = viewHolder.getAdapterPosition();
                    Category categoryToBeRemoved = userCategories.get(elementPosition);
                    List<Note> notesToBeRemoved = categoryToBeRemoved.getNotes();
                    int count = notesToBeRemoved.size();
                    for (int i = 0; i < count; i++) {
                        //if there are any reminders set for the note delete them also
                        if(!notesToBeRemoved.get(i).getNoteReminders().isEmpty()){
                            NoteReminder.delete(notesToBeRemoved.get(i).getNoteReminders().get(0));
                        }
                        Note.delete(notesToBeRemoved.get(i));
                    }
                    Category.delete(categoryToBeRemoved);
                    userCategories.remove(elementPosition);
                    mRecyclerView.getAdapter().notifyItemRemoved(elementPosition);
                }

            });


    @Override
    public boolean onQueryTextSubmit(String query) {
        CategoriesAdapter categoriesAdapter = (CategoriesAdapter) mRecyclerView.getAdapter();
        categoriesAdapter.filter(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        CategoriesAdapter categoriesAdapter = (CategoriesAdapter) mRecyclerView.getAdapter();
        categoriesAdapter.filter(newText);
        return true;
    }

    @Override
    public void categoryEdited(Category category,int position) {
            userCategories.set(position,category);
            CategoriesAdapter categoriesAdapter = (CategoriesAdapter) mRecyclerView.getAdapter();
            categoriesAdapter.updateCategories(userCategories);
    }
}
