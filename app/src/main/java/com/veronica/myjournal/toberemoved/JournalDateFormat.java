package com.veronica.myjournal.toberemoved;

/**
 * Created by Veronica on 9/28/2016.
 */
public class JournalDateFormat {

    private int day;
    private int month;
    private int year;

    public JournalDateFormat(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getDay() {
        return day;
    }
    private String getFullDate(){
        return this.getDay()+ " / "+ this.getMonth()+ " / "+this.getYear();
    }
}
