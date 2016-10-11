package com.veronica.medaily.validationmodels;

import com.veronica.medaily.Constants;
import com.veronica.medaily.helpers.InputValidator;

import java.util.InvalidPropertiesFormatException;

/**
 * Created by Veronica on 10/8/2016.
 */
public class CategoryValidationModel {

    private String _title;
    private String _content;

    public CategoryValidationModel(String _title, String _content) throws InvalidPropertiesFormatException {
        this.set_title(_title);
        this.set_content(_content);
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) throws InvalidPropertiesFormatException {
        if(!InputValidator.isMinLenghRestricted(Constants.CATEGORY_TITLE_MIN_LENGTH,_title)){
            throw new InvalidPropertiesFormatException("Invalid name. Minimum "+Constants.CATEGORY_TITLE_MIN_LENGTH+" characters");
        }
        this._title = _title;
    }

    public String get_content() {
        return _content;
    }

    public void set_content(String _content) throws InvalidPropertiesFormatException {
        if(!InputValidator.isMinLenghRestricted(Constants.CATEGORY_CONTENT_MIN_LENGTH,_content)){
            throw new InvalidPropertiesFormatException("Invalid description. Minimum "+ Constants.CATEGORY_CONTENT_MIN_LENGTH+" characters");
        }
        this._content = _content;
    }
}
