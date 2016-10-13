package com.veronica.medaily.fragments;

/**
 * Created by Veronica on 10/13/2016.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.veronica.medaily.R;
import com.veronica.medaily.adapters.NotesAdapter;
import com.veronica.medaily.dbmodels.Category;
import com.veronica.medaily.dbmodels.Note;
import com.veronica.medaily.dbmodels.NoteReminder;
import com.veronica.medaily.dialogs.EditNoteDialog;
import com.veronica.medaily.listeners.INoteEditedListener;
import com.veronica.medaily.listeners.RecyclerClickListener;

import java.util.List;

public class NotesByCategory extends BaseFragment implements SearchView.OnQueryTextListener,INoteEditedListener {

    private DrawerLayout drawerLayout;

    private NotesAdapter mNotesAdapter;
    private SearchView mSearchViewNotes;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar progressBar;
    private ItemTouchHelper itemTouchHelper;
    private NoteDetailsFragment noteDetailsFragment;
    private List<Note> userNotes;
    private NotesByCategory notesFragment;
    private Category category;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if(bundle!=null){
            long categoryId = bundle.getLong("category_id");
            category = Category.findById(Category.class,categoryId);
        }
        initializeItemTouchHelper();
        drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        notesFragment = this;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notes,container,false);

        mSearchViewNotes = (SearchView) view.findViewById(R.id.search_view_notes);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_notes);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar_notes);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        mSearchViewNotes.setOnQueryTextListener(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        this.userNotes = category.getNotes();
        this.mNotesAdapter = new NotesAdapter(userNotes);
        mRecyclerView.setAdapter(mNotesAdapter);
        progressBar.setVisibility(View.GONE);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerClickListener(getContext(), mRecyclerView ,new RecyclerClickListener.OnItemClickListener() {

                    @Override public void onItemClick(View view, int position) {
                        noteDetailsFragment = new NoteDetailsFragment();
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("note_id", String.valueOf(userNotes.get(position).getId()));
                        noteDetailsFragment.setArguments(bundle1);
                        placeFragment(noteDetailsFragment);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        //drawerLayout interacts some how with gesture detector and sometimes activate onLongPress
                        //so added this check
                        boolean isOpen = drawerLayout.isDrawerOpen(GravityCompat.START);
                        if(!isOpen) {
                            EditNoteDialog editNoteDialog = new EditNoteDialog(getContext(),userNotes.get(position),position,notesFragment);
                            editNoteDialog.show();
                        }
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
                        NotesAdapter notesAdapter = (NotesAdapter) mRecyclerView.getAdapter();
                        notesAdapter.deleteNote(elementPosition);
                        Note.delete(noteToBeRemoved);
                    }

                });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        NotesAdapter notesAdapter = (NotesAdapter) mRecyclerView.getAdapter();
        if(notesAdapter!=null) {
            notesAdapter.filter(query);
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        NotesAdapter notesAdapter = (NotesAdapter) mRecyclerView.getAdapter();
        if(notesAdapter!=null) {
            notesAdapter.filter(newText);
        }
        return true;
    }

    @Override
    public void noteEdited(Note note, int position) {
        userNotes.set(position,note);
        NotesAdapter notesAdapter = (NotesAdapter) mRecyclerView.getAdapter();
        notesAdapter.updateNotes(userNotes);
    }
}
