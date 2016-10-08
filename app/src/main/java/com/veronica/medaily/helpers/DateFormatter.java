package com.veronica.medaily.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Veronica on 10/9/2016.
 */
public final class DateFormatter {

    public static SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static Date fromStringToDate(String date){
        Date dateResult = null;
        try {
            dateResult = formater.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateResult;

    }

    public static String fromDateToString(Date date){

        String datetime = formater.format(date);
        return datetime;
    }
}
