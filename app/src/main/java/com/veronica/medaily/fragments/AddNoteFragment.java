package com.veronica.medaily.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.veronica.medaily.Constants;
import com.veronica.medaily.R;
import com.veronica.medaily.bindingmodels.NoteBindingModel;
import com.veronica.medaily.broadcasts.AlarmReceiver;
import com.veronica.medaily.dbmodels.Category;
import com.veronica.medaily.dbmodels.Note;
import com.veronica.medaily.dbmodels.NoteReminder;
import com.veronica.medaily.dialogs.ReminderDatePicker;
import com.veronica.medaily.helpers.DateHelper;
import com.veronica.medaily.helpers.NotificationHandler;
import com.veronica.medaily.interfaces.IDatePicked;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.InvalidPropertiesFormatException;
import java.util.List;


/**
 * Created by Veronica on 9/30/2016.
 */
public class AddNoteFragment extends BaseFragment implements View.OnClickListener ,AdapterView.OnItemSelectedListener,IDatePicked{

    private NotificationHandler notificationHandler;

    private TextView mTxtViewAddNote;
    private EditText mEditTxtNoteTitle;
    private EditText mEditTxtNoteContent;
    private Spinner mCategoriesSpinner;
    private Button mBtnAddPhoto;
    private Button mBtnAddReminder;
    private Button mBtnSave;

    private String mPhotoUri;
    private List<String> categories;
    private long mSpinnerSelectedPosition = 0;

    ReminderDatePicker datePicker;
    Calendar pickedDate;

    private Note mNote;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.notificationHandler = new NotificationHandler(getContext());
        List<Category> fullCategories = mCurrentUser.getCategories();
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
        mCategoriesSpinner = (Spinner) view.findViewById(R.id.categories_spinner);

        mBtnAddPhoto = (Button) view.findViewById(R.id.btn_note_add_img);
        mBtnAddReminder = (Button) view.findViewById(R.id.btn_note_add_reminder);
        mBtnSave = (Button) view.findViewById(R.id.btn_create_note);

        mBtnAddPhoto.setOnClickListener(this);
        mBtnAddReminder.setOnClickListener(this);
        mBtnSave.setOnClickListener(this);

        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_item_layout,categories);
        categoriesAdapter.setDropDownViewResource(R.layout.spinner_item_dropdown_layout);
        mCategoriesSpinner.setAdapter(categoriesAdapter);
        mCategoriesSpinner.setOnItemSelectedListener(this);

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
            String selectedCategory;

            if(mCategoriesSpinner != null && mCategoriesSpinner.getSelectedItem() !=null ) {
                selectedCategory = (String)mCategoriesSpinner.getSelectedItem();
                Category category = Category.find(Category.class," name = ? ",selectedCategory).get(0);

                try {
                    Date currentDate = Calendar.getInstance().getTime();
                    String dateAsString = DateHelper.fromDateToString(currentDate);

                    NoteBindingModel noteBindingModel = new NoteBindingModel(title,content,selectedCategory,dateAsString,mPhotoUri);

                    Note note = new Note(category,mCurrentUser,title,content,dateAsString,mPhotoUri);
                    note.save();
                    notificationHandler.toastSuccessNotificationBottom("Note added successfully");

                    if(pickedDate!=null){
                        setupAlarm(note);
                    }

                } catch (InvalidPropertiesFormatException e) {
                    notificationHandler.toastWarningNotificationBottom(e.getMessage());
                }

            } else  {
                notificationHandler.toastNeutralNotificationBottom("Please create category first");
            }


        } else if (v.getId() == R.id.btn_note_add_img) {
            openGallery();
        }else if (v.getId() == R.id.btn_note_add_reminder) {
            datePicker = new ReminderDatePicker(getContext(),this);
            datePicker.show();
        }
    }

    private void setupAlarm(Note note) {

        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
        Date pickedDateToDate  = pickedDate.getTime();

        Calendar reminderCalendar = Calendar.getInstance();
        reminderCalendar.setTime(pickedDateToDate);

        NoteReminder reminder = new NoteReminder(mCurrentUser,note, DateHelper.fromDateToString(reminderCalendar.getTime()));
        reminder.save();

        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        intent.putExtra("alarm_id", reminder.getId());
        intent.putExtra("alarm_title", note.getTitle());
        intent.putExtra("alarm_content", note.getContent());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), reminder.getId().intValue(), intent, 0);

        alarmManager.set(AlarmManager.RTC_WAKEUP, reminderCalendar.getTimeInMillis(), pendingIntent);
    }

    // used for spinner - updates position
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mCategoriesSpinner.setSelection(position);
    }
    // used for spinner - updates position
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        mCategoriesSpinner.setSelection(0);
    }

    @Override
    public void pick(Calendar pickedDateTime) {
        this.pickedDate = pickedDateTime;
    }
}
