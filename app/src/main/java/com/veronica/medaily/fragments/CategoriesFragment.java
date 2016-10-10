package com.veronica.medaily.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.veronica.medaily.R;
import com.veronica.medaily.adapters.CategoriesAdapter;
import com.veronica.medaily.dbmodels.Category;
import com.veronica.medaily.dbmodels.Note;
import com.veronica.medaily.dbmodels.NoteReminder;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class CategoriesFragment extends BaseFragment implements android.widget.SearchView.OnQueryTextListener {

    private SearchView mSearchViewCategories;
    private RecyclerView mRecyclerView;
    private CategoriesAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Category> userCategories;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.userCategories = mCurrentUser.getCategories();
        Collections.sort(userCategories, new Comparator<Category>() {
            @Override
            public int compare(Category lhs, Category rhs) {
                return lhs.getName().toLowerCase().compareTo(rhs.getName().toLowerCase());
            }
        });
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories,container,false);

        mSearchViewCategories = (SearchView) view.findViewById(R.id.search_view_categories);
        mSearchViewCategories.setOnQueryTextListener(this);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_categories);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        mAdapter = new CategoriesAdapter(userCategories);
        mRecyclerView.setAdapter(mAdapter);
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
                        //if there are any reminders set for the note delete it
                        if(!notesToBeRemoved.get(i).getNoteReminders().isEmpty()){
                            NoteReminder.delete(notesToBeRemoved.get(i).getNoteReminders().get(0));
                        }
                        Note.delete(notesToBeRemoved.get(i));
                    }
                    Category.delete(categoryToBeRemoved);
                    userCategories.remove(elementPosition);
                    mAdapter.notifyItemRemoved(elementPosition);
                }

            });


    @Override
    public boolean onQueryTextSubmit(String query) {
        mAdapter.filter(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mAdapter.filter(newText);
        return true;
    }
}
