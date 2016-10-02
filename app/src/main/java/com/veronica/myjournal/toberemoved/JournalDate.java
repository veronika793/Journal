package com.veronica.myjournal.toberemoved;

/**
 * Created by Veronica on 10/2/2016.
 */
public class JournalDate {

    private Integer day;
    private Integer month;
    private Integer year;
    private Integer hour;
    private Integer minute;
    private Integer seconds;

    public JournalDate(Integer day, Integer year, Integer month, Integer hour, Integer minute, Integer seconds) {
        this.setDay(day);
        this.setMonth(month);
        this.setYear(year);
        this.setHour(hour);
        this.setMinute(minute);
        this.setSeconds(seconds);

    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }

    @Override
    public String toString() {
        return this.getDay()+"/"+this.getMonth()+"/"+this.getYear()+" "+this.getHour()+":"+this.getMinute()+":"+this.getSeconds();
    }
}
