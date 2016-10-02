package com.veronica.myjournal.models;

import java.util.Date;

/**
 * Created by Veronica on 9/28/2016.
 */
public class User {

    private int id;
    private String username;
    private String password;
    private Date  birthData;
    private boolean isAdmin;

    public User(String username, String password, Date birthData) {
        this.username = username;
        this.password = password;
        this.birthData = birthData;
        this.isAdmin = false;
    }


}
