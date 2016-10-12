package com.veronica.medaily.adapters;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.api.client.repackaged.org.apache.commons.codec.binary.StringUtils;
import com.veronica.medaily.Constants;
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
        public TextView noteInfoIcon;
        public ViewHolder(View v) {
            super(v);
            noteContainer = v.findViewById(R.id.container_notes);
            noteTitle = (TextView) v.findViewById(R.id.txt_note_title);
            noteCreatedOn = (TextView) v.findViewById(R.id.txt_note_createdon);
            noteContent = (TextView) v.findViewById(R.id.txt_note_content);
            noteReminder = (TextView) v.findViewById(R.id.txt_note_reminder);
            noteInfoIcon = (TextView) v.findViewById(R.id.txt_icon_info);
        }
    }

    public NotesAdapter(List<Note> notes) {
        this.mNotes = notes;
        this.mNotesCopy.addAll(notes);
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
        if(!(mNotesCopy.get(position).getReminderDate() == null)){
            holder.noteReminder.setVisibility(View.VISIBLE);
            String reminderDate =  mNotes.get(position).getReminderDate().replace(" ","\n");
            holder.noteCreatedOn.setText(reminderDate);
        }else{
            holder.noteInfoIcon.setVisibility(View.VISIBLE);
        }

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
