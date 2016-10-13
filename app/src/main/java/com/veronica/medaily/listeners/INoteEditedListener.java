package com.veronica.medaily.listeners;
import com.veronica.medaily.dbmodels.Note;

/**
 * Created by Veronica on 10/11/2016.
 */
public interface INoteEditedListener {
    void noteEdited(Note note, int position);
}
