package com.ss.androidstoragesystemstutorial.sharedpref;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ss.androidstoragesystemstutorial.R;

public class SharedPreferenceSampleActivity extends AppCompatActivity {
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText ageEditText;
    private SwitchCompat switchCompat;
    private PersonalInfoSharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference_sample);
        setupViews();
        sharedPrefManager = new PersonalInfoSharedPrefManager(this);
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
        final Button clearAllButton = findViewById(R.id.button_sharedPref_clearAll);
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
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefManager.savePersonalInfo(
                        firstNameEditText.getText().toString(),
                        lastNameEditText.getText().toString(),
                        Integer.parseInt(ageEditText.getText().toString()),
                        switchCompat.isChecked()
                );
                clearAllButton.performClick();
                Toast.makeText(SharedPreferenceSampleActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
        });
        Button loadButton = findViewById(R.id.button_sharedPref_load);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstNameEditText.setText(sharedPrefManager.getFirstName());
                lastNameEditText.setText(sharedPrefManager.getLastName());
                ageEditText.setText(String.valueOf(sharedPrefManager.getAge()));
                switchCompat.setChecked(sharedPrefManager.getIsAndroidExpertStudent());
            }
        });
    }


}
