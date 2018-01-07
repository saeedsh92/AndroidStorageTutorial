package com.ss.androidstoragesystemstutorial.sharedpref;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ss.androidstoragesystemstutorial.R;

public class SharedPreferenceSampleActivity extends AppCompatActivity {
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText ageEditText;
    private SwitchCompat switchCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference_sample);
        setupViews();
    }

    private void setupViews() {
        Toolbar toolbar = findViewById(R.id.toolbar_sharedPref);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        firstNameEditText = findViewById(R.id.et_sharedPref_firstName);
        lastNameEditText = findViewById(R.id.et_sharedPref_lastName);
        ageEditText = findViewById(R.id.et_sharedPref_age);
        switchCompat = findViewById(R.id.switch_sharedPref_isAndroidExpertStudent);
        Button clearAllButton = findViewById(R.id.button_sharedPref_clearAll);
        clearAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstNameEditText.setText("");
                lastNameEditText.setText("");
                ageEditText.setText("");
                switchCompat.setChecked(false);
            }
        });
        Button saveButton = findViewById(R.id.button_sharedPref_save);
        Button loadButton = findViewById(R.id.button_sharedPref_load);
    }

}
