package com.veronica.medaily.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.veronica.medaily.R;
import com.veronica.medaily.dbmodels.Category;
import com.veronica.medaily.dbmodels.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Veronica on 10/9/2016.
 */
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private List<Note> mNotes = new ArrayList<>();
    private List<Note> mNotesCopy = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View noteContainer;
        public TextView noteTitle;
        public TextView noteCreatedOn;
        public TextView noteContent;
        public TextView noteReminder;
        public TextView noteCategory;

        public ViewHolder(View v) {
            super(v);
            noteContainer = v.findViewById(R.id.container_notes);
            noteTitle = (TextView) v.findViewById(R.id.txt_note_title);
            noteCreatedOn = (TextView) v.findViewById(R.id.txt_note_createdon);
            noteContent = (TextView) v.findViewById(R.id.txt_note_content);
            noteReminder = (TextView) v.findViewById(R.id.txt_note_reminder);
            noteCategory = (TextView) v.findViewById(R.id.txt_note_category);
        }
    }

    public NotesAdapter(List<Note> categories) {
        this.mNotes = categories;
        this.mNotesCopy.addAll(categories);
    }

    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_notes, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String hexColor = String.format("#%06X", (0xFFFFFF & mNotes.get(position).getCategory().getColor()));
        holder.noteContainer.setBackgroundColor(Color.parseColor(hexColor));
        holder.noteTitle.setText(mNotes.get(position).getTitle());
        holder.noteContent.setText(mNotes.get(position).getContent());
        holder.noteCategory.setText(mNotes.get(position).getCategory().getName());
        holder.noteCreatedOn.setText(mNotes.get(position).getCreatedOnDate());
        holder.noteReminder.setText(mNotes.get(position).getReminderDate());

    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public void filter(String text) {
        mNotes.clear();
        if(text.isEmpty()){
            mNotes.addAll(mNotesCopy);
        } else{
            text = text.toLowerCase();
            for (Note itemCopy : mNotesCopy) {
                if(itemCopy.getTitle().toLowerCase().contains(text) ||
                        itemCopy.getContent().toLowerCase().contains(text) ||
                            itemCopy.getCategory().getName().toLowerCase().contains(text)){
                    mNotes.add(itemCopy);
                }
            }
        }
        this.notifyDataSetChanged();
    }

    public void updateNotes(List<Note> notes){
        mNotes = notes;
        mNotesCopy.clear();
        mNotesCopy.addAll(notes);
        notifyDataSetChanged();
    }

    public void deleteNote(Note note){
        this.mNotes.remove(note);
        this.mNotesCopy.remove(note);
        notifyDataSetChanged();
    }

    public List<Note> getNotes(){
        return mNotes;
    }
}
