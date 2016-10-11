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
import com.veronica.medaily.adapters.NotesAdapter;
import com.veronica.medaily.dbmodels.Category;
import com.veronica.medaily.dbmodels.Note;
import com.veronica.medaily.dbmodels.NoteReminder;
import com.veronica.medaily.dialogs.CategoriesDetailsDialog;
import com.veronica.medaily.dialogs.EditCategoryDialog;
import com.veronica.medaily.loaders.CategoriesLoader;
import com.veronica.medaily.loaders.NotesLoader;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class NotesFragment extends BaseFragment implements android.widget.SearchView.OnQueryTextListener {

    private SearchView mSearchViewNotes;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar progressBar;
    private ItemTouchHelper itemTouchHelper;

    private List<Note> userNotes;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeItemTouchHelper();
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes,container,false);
        mSearchViewNotes = (SearchView) view.findViewById(R.id.search_view_notes);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_notes);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar_notes);
        mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mSearchViewNotes.setOnQueryTextListener(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        try {
            this.userNotes = new NotesLoader(progressBar,mCurrentUser,mRecyclerView).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        mRecyclerView.addOnItemTouchListener(
                new RecyclerClickListener(getContext(), mRecyclerView ,new RecyclerClickListener.OnItemClickListener() {

                    @Override public void onItemClick(View view, int position) {
                        Log.d("DEBUG", "item clicked");
                    }

                    @Override public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onDoubleTab(View view, int position) {

                    }
                })
        );
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        return view;
    }

    private void initializeItemTouchHelper() {
        this.itemTouchHelper = new ItemTouchHelper(

                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        ItemTouchHelper.RIGHT) {

                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int elementPosition = viewHolder.getAdapterPosition();
                        Note noteToBeRemoved = userNotes.get(elementPosition);

                        if(noteToBeRemoved.getNoteReminders().size()>0) {
                            NoteReminder.delete(noteToBeRemoved.getNoteReminders().get(0));
                        }
                        Note.delete(noteToBeRemoved);
                        userNotes.remove(elementPosition);
                        mRecyclerView.getAdapter().notifyItemRemoved(elementPosition);
                    }

                });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        NotesAdapter notesAdapter = (NotesAdapter) mRecyclerView.getAdapter();
        notesAdapter.filter(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        NotesAdapter notesAdapter = (NotesAdapter) mRecyclerView.getAdapter();
        notesAdapter.filter(newText);
        return true;
    }

}
