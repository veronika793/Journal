package com.veronica.medaily.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.veronica.medaily.R;
import com.veronica.medaily.helpers.NotificationHandler;
import com.veronica.medaily.interfaces.IDatePicked;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Veronica on 10/9/2016.
 */
public class ReminderDatePicker extends Dialog implements View.OnClickListener{

    private IDatePicked mListener;
    private NotificationHandler notificationHandler;

    private Button mBtnDateSelected;
    private Button mBtnCancel;

    private DatePicker mDatePicker;
    private EditText mHoursPicked;
    private EditText mMinutesPicked;

    private int mDay;
    private int mMonth;
    private int mYear;
    private int mHours;
    private int mMinutes;

    public ReminderDatePicker(Context context,IDatePicked datePickedListener) {
        super(context);
        this.mListener = datePickedListener;
        notificationHandler = new NotificationHandler(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_datepicker);
        setTitle("Select date");

        mDatePicker = (DatePicker) findViewById(R.id.datePicker1);
        mHoursPicked = (EditText) findViewById(R.id.edit_txt_hours);
        mMinutesPicked = (EditText) findViewById(R.id.edit_txt_minutes);

        mBtnDateSelected = (Button)findViewById(R.id.btn_date_picker);
        mBtnCancel = (Button)findViewById(R.id.btn_date_picker_cancel);
        mBtnDateSelected.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_date_picker_cancel){
            notificationHandler.toastNeutralNotificationBottom("No date has been picked");
            this.mListener.pick(null);
            this.dismiss();
        }else if(v.getId()== R.id.btn_date_picker){
            //check if edit texts are not empty
            if (mHoursPicked.getText().toString().length() > 0 && mMinutesPicked.getText().toString().trim().length() > 0) {

                this.mDay = this.mDatePicker.getDayOfMonth();
                this.mMonth = this.mDatePicker.getMonth();
                this.mYear = this.mDatePicker.getYear();
                this.mHours = Integer.valueOf(mHoursPicked.getText().toString());
                this.mMinutes = Integer.valueOf(mMinutesPicked.getText().toString());

                //check if selected hours/minutes are valid
                if (mHours >= 0 && mHours <= 24 && mMinutes >= 0 && mMinutes <= 60) {
                    Calendar dateToReturn  = Calendar.getInstance();
                    dateToReturn.set(mYear,mMonth,mDay,mHours,mMinutes,0);

                    //check if the selected date is passed already
                    if (dateToReturn.getTime().before(new Date())) {
                        this.mListener.pick(null);
                        notificationHandler.toastNeutralNotificationBottom("Selected date is already passed");
                    }else{
                        notificationHandler.toastNeutralNotificationBottom("Date picked");
                        this.mListener.pick(dateToReturn);
                        this.dismiss();
                    }

                }else{
                    notificationHandler.toastNeutralNotificationBottom("Please enter valid hours/minute values");
                }
            }else{
                notificationHandler.toastNeutralNotificationBottom("Please enter valid hours/minute values");
            }

        }
    }
}
