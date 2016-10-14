package com.veronica.medaily.fragments;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.veronica.medaily.dialogs.CategoriesPreviewDialog;
import com.veronica.medaily.dialogs.NotesPreviewDialog;
import com.veronica.medaily.listeners.RecyclerClickListener;
import com.veronica.medaily.adapters.NotesAdapter;
import com.veronica.medaily.dbmodels.Category;
import com.veronica.medaily.dbmodels.Note;
import com.veronica.medaily.dbmodels.NoteReminder;
import com.veronica.medaily.dialogs.EditNoteDialog;
import com.veronica.medaily.listeners.INoteEditedListener;
import com.veronica.medaily.loaders.NotesLoader;

import java.util.List;

public class NotesFragment extends BaseFragment implements android.widget.SearchView.OnQueryTextListener,INoteEditedListener {

    private DrawerLayout drawerLayout;

    private NotesAdapter mNotesAdapter;
    private SearchView mSearchViewNotes;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ItemTouchHelper itemTouchHelper;
    private List<Note> userNotes;
    private NotesFragment notesFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeItemTouchHelper();
        drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        notesFragment = this;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notes,container,false);
        mSearchViewNotes = (SearchView) view.findViewById(R.id.search_view_notes);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_notes);
        mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mSearchViewNotes.setOnQueryTextListener(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

//        this.userNotes = mCurrentUser.getNotes();
//        mNotesAdapter = new NotesAdapter(userNotes);
//        mRecyclerView.setAdapter(mNotesAdapter);
        try {
            this.userNotes = new NotesLoader(mCurrentUser,mRecyclerView).execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mRecyclerView.addOnItemTouchListener(
                new RecyclerClickListener(getContext(), mRecyclerView ,new RecyclerClickListener.OnItemClickListener() {

                    @Override public void onItemClick(View view, int position) {
                        NoteDetailsFragment noteDetailsFragment = new NoteDetailsFragment();
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("note_id", String.valueOf(userNotes.get(position).getId()));
                        //crashed once here so made this overall useless check
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
                        NotesPreviewDialog detailsDialog = new NotesPreviewDialog(getContext(),userNotes.get(position));
                        detailsDialog.show();
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