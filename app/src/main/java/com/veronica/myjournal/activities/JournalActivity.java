package com.veronica.myjournal.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.veronica.myjournal.R;
import com.veronica.myjournal.app.MyJournalApplication;

public class JournalActivity extends AppCompatActivity {

    MyJournalApplication app;

    TextView txtView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (MyJournalApplication)this.getApplication();
        setContentView(R.layout.activity_journal);

        if(!app.getAuthorizationManager().isLoggedIn()){
            startActivity(new Intent(JournalActivity.this,LoginActivity.class));
        }

        txtView = (TextView) findViewById(R.id.txt_journal);

//        String customFont = "IndieFlower.ttf";
//        Typeface typeface = Typeface.createFromAsset(getAssets(),customFont);
//        txtView.setTypeface(typeface);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }
}
