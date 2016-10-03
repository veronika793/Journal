package com.veronica.myjournal.models;

/**
 * Created by Veronica on 9/30/2016.
 */
public class Note {

    private Integer _id;
    private Integer _user_id;
    private String _title;
    private String _content;
    private String _created_on;
    private String _photo;
    private String _location;


    public Note(Integer id, Integer user_id, String  title, String  content, String created_on, String photo, String location) {
        this.set_id(id);
        this.set_user_id(user_id);
        this.set_title(title);
        this.set_content(content);
        this.set_created_on(created_on);
        this.set_photo(photo);
        this.set_location(location);
    }

    public Note(Integer id,Integer user_id, String title, String content, String created_on) {
        this(id,user_id,title,content,created_on,null,null);
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public Integer get_user_id() {
        return _user_id;
    }

    public void set_user_id(Integer _user_id) {
        this._user_id = _user_id;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_content() {
        return _content;
    }

    public void set_content(String _content) {
        this._content = _content;
    }

    public String get_created_on() {
        return _created_on;
    }

    public void set_created_on(String _created_on) {
        this._created_on = _created_on;
    }

    public String get_photo() {
        return _photo;
    }

    public void set_photo(String _photo) {
        this._photo = _photo;
    }

    public String get_location() {
        return _location;
    }

    public void set_location(String _location) {
        this._location = _location;
    }
}
