package com.veronica.medaily.toberemoved;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.veronica.medaily.adapters.NotesAdapter;
import com.veronica.medaily.dbmodels.Note;
import com.veronica.medaily.dbmodels.User;
import com.veronica.medaily.helpers.DateHelper;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Veronica on 10/10/2016.
 */
public class NotesLoader extends AsyncTask<Void,Integer,List<Note>> {

    private RecyclerView recyclerView;
    private NotesAdapter notesAdapter;
    private User user;
    private List<Note> notes;

    @Override
    protected List<Note> doInBackground(Void... params) {

        notes = user.getNotes();
        Collections.sort(notes, new Comparator<Note>() {
            @Override
            public int compare(Note one, Note two) {
                Date first = DateHelper.fromStringToDate(one.getCreatedOnDate());
                Date second = DateHelper.fromStringToDate(two.getCreatedOnDate());
                return second.compareTo(first);
            }
        });

        return notes;
    }

    public NotesLoader(User user, RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.user = user;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(List<Note> note) {
        super.onPostExecute(note);
        notesAdapter = new NotesAdapter(notes);
        recyclerView.setAdapter(notesAdapter);
    }
}

