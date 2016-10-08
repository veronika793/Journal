package com.veronica.medaily.fragments;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.veronica.medaily.Constants;
import com.veronica.medaily.R;
import com.veronica.medaily.bindingmodels.NoteBindingModel;
import com.veronica.medaily.broadcasts.AlarmReceiver;
import com.veronica.medaily.dbmodels.Category;
import com.veronica.medaily.dbmodels.Note;
import com.veronica.medaily.dbmodels.NoteReminder;
import com.veronica.medaily.helpers.DateFormatter;
import com.veronica.medaily.helpers.NotificationHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.InvalidPropertiesFormatException;
import java.util.List;


/**
 * Created by Veronica on 9/30/2016.
 */
public class AddNoteFragment extends BaseFragment implements View.OnClickListener {

    private NotificationHandler notificationHandler;

    private TextView mTxtViewAddNote;
    private EditText mEditTxtNoteTitle;
    private EditText mEditTxtNoteContent;
    private AutoCompleteTextView autocompleteCategories;
    private Button mBtnAddPhoto;
    private Button mBtnAddReminder;
    private Button mBtnSave;

    private String mPhotoUri;

    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHours;
    private int mMinutes;

    private Button mBtnDatePickerCreate;
    private Button mBtnDatePickerCancel;
    private Dialog mDatePickerDialog;
    private DatePicker mDatePicker;
    private boolean isAddedNoteReminder = false;
    private List<String> categories;

    private Note mNote;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.notificationHandler = new NotificationHandler(getContext());
        List<Category> fullCategories = Category.find(Category.class," user = ? ", String.valueOf(mCurrentUser.getId()));
        this.categories = new ArrayList<>();
        for (Category category : fullCategories) {
            categories.add(category.getName());
        }
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_note, container, false);

        mTxtViewAddNote = (TextView)view.findViewById(R.id.txt_view_add_note);
        super.setupTypefaceView(mTxtViewAddNote);
        super.setupUnderline(mTxtViewAddNote);
        mEditTxtNoteTitle = (EditText) view.findViewById(R.id.edit_txt_note_title);
        mEditTxtNoteContent = (EditText) view.findViewById(R.id.edit_txt_note_content);
        autocompleteCategories = (AutoCompleteTextView) view.findViewById(R.id.autocomplete_categories);

        mBtnAddPhoto = (Button) view.findViewById(R.id.btn_note_add_img);
        mBtnAddReminder = (Button) view.findViewById(R.id.btn_note_add_reminder);
        mBtnSave = (Button) view.findViewById(R.id.btn_create_note);

        mBtnAddPhoto.setOnClickListener(this);
        mBtnAddReminder.setOnClickListener(this);
        mBtnSave.setOnClickListener(this);

        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(getContext(),R.layout.autocomplete_layout,categories);
        autocompleteCategories.setAdapter(categoriesAdapter);

        return view;
    }

    private void openGallery() {
        Intent intent;
        if (Build.VERSION.SDK_INT > 18) {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        } else {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        }
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, Constants.PICK_NOTE_IMAGE_REQ_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.RESULT_OK && requestCode == Constants.PICK_NOTE_IMAGE_REQ_CODE) {
            Log.d("DEBUG", "PHOTO PICKED");
            Uri selectedImage = data.getData();
            this.mPhotoUri = selectedImage.toString();
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_create_note) {

            String title = mEditTxtNoteTitle.getText().toString().trim();
            String content = mEditTxtNoteContent.getText().toString().trim();
            String category = autocompleteCategories.getText().toString().trim();

            List<Category> choosenCategory = Category.find(Category.class," name = ? ",category);

            if(choosenCategory.size()>0) {
                Date currentDate = Calendar.getInstance().getTime();
                String dateAsString = DateFormatter.fromDateToString(currentDate);

                try {
                    NoteBindingModel noteBindingModel = new NoteBindingModel(title,content,category,dateAsString,mPhotoUri);

                    Note note = new Note(choosenCategory.get(0),mCurrentUser,title,content,dateAsString,mPhotoUri);
                    note.save();
                    notificationHandler.toastSuccessNotificationBottom("Note added successfully");

                    if(isAddedNoteReminder){
                        startAlarm(note);
                    }

                } catch (InvalidPropertiesFormatException e) {
                    notificationHandler.toastWarningNotificationBottom(e.getMessage());
                }

            }else{
                notificationHandler.toastNeutralNotificationBottom("Please choose existing category");
            }

        } else if (v.getId() == R.id.btn_note_add_img) {
            openGallery();
        }else if (v.getId() == R.id.btn_note_add_reminder) {

            this.mDatePickerDialog = new Dialog(getContext());
            this.mDatePickerDialog.setContentView(R.layout.datepicker_layout);
            this.mDatePickerDialog.setTitle("Select date");

            this.mDatePickerDialog.show();
            mBtnDatePickerCreate = (Button) mDatePickerDialog.findViewById(R.id.btn_date_picker);
            mBtnDatePickerCancel = (Button) mDatePickerDialog.findViewById(R.id.btn_date_picker_cancel);
            mBtnDatePickerCreate.setOnClickListener(this);
            mBtnDatePickerCancel.setOnClickListener(this);

        } else if (v.getId() == R.id.btn_date_picker) {
            this.mDatePicker = (DatePicker) this.mDatePickerDialog.findViewById(R.id.datePicker1);
            this.mDay = this.mDatePicker.getDayOfMonth();
            this.mYear = this.mDatePicker.getYear();
            this.mMonth = this.mDatePicker.getMonth();

            EditText hoursEditTxt = (EditText) this.mDatePickerDialog.findViewById(R.id.edit_txt_hours);
            EditText minutesEditTxt = (EditText) this.mDatePickerDialog.findViewById(R.id.edit_txt_minutes);

            if (hoursEditTxt.getText().toString().toString().length() > 0 && minutesEditTxt.getText().toString().trim().length() > 0) {

                this.mHours = Integer.valueOf(hoursEditTxt.getText().toString());
                this.mMinutes = Integer.valueOf(minutesEditTxt.getText().toString());

                if (mHours >= 0 && mHours <= 24 && mMinutes >= 0 && mMinutes <= 60) {
                    this.mDatePickerDialog.dismiss();
                    this.isAddedNoteReminder = true;
                }else{
                    this.isAddedNoteReminder = false;
                }
            }

        } else if (v.getId() == R.id.btn_date_picker_cancel) {
            this.mDatePickerDialog.dismiss();
            this.mDatePicker = null;
            this.mDatePickerDialog = null;
            this.isAddedNoteReminder = false;
        }
    }

    private void startAlarm(Note note) {



        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, mDay);
        calendar.set(Calendar.MONTH, Calendar.OCTOBER);  //first month is 0
        calendar.set(Calendar.YEAR, mYear);
        calendar.set(Calendar.HOUR_OF_DAY, mHours);
        calendar.set(Calendar.MINUTE, mMinutes);
        calendar.set(Calendar.SECOND, 0);

        NoteReminder reminder = new NoteReminder(note,DateFormatter.fromDateToString(calendar.getTime()));
        reminder.save();

        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        intent.putExtra("alarm_id", reminder.getId());
        intent.putExtra("alarm_title", note.getTitle());
        intent.putExtra("alarm_content", note.getContent());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), reminder.getId().intValue(), intent, 0);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

}
