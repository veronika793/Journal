package com.veronica.medaily.bindingmodels;

import android.os.Parcel;
import android.os.Parcelable;

import com.veronica.medaily.Constants;
import com.veronica.medaily.helpers.InputValidator;

import java.util.InvalidPropertiesFormatException;

/**
 * Created by Veronica on 9/30/2016.
 */
public class NoteBindingModel{

    private String _title;
    private String _content;
    private String _category;
    private String _createdOn;
    private String _photoUrl;


    public NoteBindingModel(String  title, String  content,String _category) throws InvalidPropertiesFormatException {
        this.set_title(title);
        this.set_content(content);
        this.set_category(_category);
    }

    public NoteBindingModel(String _title, String _content, String _category, String _createdOn, String _photoUrl) throws InvalidPropertiesFormatException {
        this.set_title(_title);
        this.set_content(_content);
        this.set_category(_category);
        this._createdOn = _createdOn;
        this._photoUrl = _photoUrl;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) throws InvalidPropertiesFormatException {
        if(!InputValidator.isMinLenghRestricted(Constants.NOTE_TITLE_MIN_LENGTH,_title)){
            throw new InvalidPropertiesFormatException("Invalid title. Minimum "+Constants.NOTE_TITLE_MIN_LENGTH + " characters");
        }
        this._title = _title;
    }

    public String get_content() {
        return _content;
    }

    public void set_content(String _content) throws InvalidPropertiesFormatException {
        if(!InputValidator.isMinLenghRestricted(Constants.NOTE_CONTENT_MIN_LENGTH,_title)){
            throw new InvalidPropertiesFormatException("Invalid content. Minimum "+Constants.NOTE_CONTENT_MIN_LENGTH + " characters");
        }
        this._content = _content;
    }

    public String get_category() {
        return _category;
    }

    public void set_category(String _category) throws InvalidPropertiesFormatException {
        if(!InputValidator.isMinLenghRestricted(Constants.CATEGORY_TITLE_MIN_LENGTH,_title)){
            throw new InvalidPropertiesFormatException("Invalid category title. Minimum "+Constants.CATEGORY_TITLE_MIN_LENGTH + " characters");
        }
        this._category = _category;
    }

    public String get_createdOn() {
        return _createdOn;
    }

    public void set_createdOn(String _createdOn) {
        this._createdOn = _createdOn;
    }

    public String get_photoUrl() {
        return _photoUrl;
    }

    public void set_photoUrl(String _photoUrl) {
        this._photoUrl = _photoUrl;
    }
}
