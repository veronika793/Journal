package com.veronica.myjournal.toberemoved;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;


import com.veronica.myjournal.toberemoved.IDatePicker;
import com.veronica.myjournal.toberemoved.JournalDateFormat;

import java.util.Calendar;

/**
 * Created by Veronica on 9/28/2016.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    IDatePicker datePickerListener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.datePickerListener = (IDatePicker) context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog dialog = new DatePickerDialog(getActivity(),android.R.style.Theme_Holo_Light_Dialog_MinWidth, this, year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        JournalDateFormat date = new JournalDateFormat(day,month,year);
        datePickerListener.setDate(date);
    }
}
