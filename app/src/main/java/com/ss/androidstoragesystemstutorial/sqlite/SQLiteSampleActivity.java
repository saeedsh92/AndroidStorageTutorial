package com.ss.androidstoragesystemstutorial.sqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ss.androidstoragesystemstutorial.R;

public class SQLiteSampleActivity extends AppCompatActivity {

    private EditText nameET;
    private EditText phoneNumberET;
    private Button addContactBTN;
    private View showAllContactsBTN;
    private TextView contactsCountTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_sample);
        setupViews();
        updateContactsCount();
    }

    private void setupViews() {
        Toolbar toolbar = findViewById(R.id.toolbar_sqliteSample);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        nameET = findViewById(R.id.et_sqliteSample_name);
        phoneNumberET = findViewById(R.id.et_sqliteSample_phoneNumber);
        addContactBTN = findViewById(R.id.button_sqliteSample_addContact);
        contactsCountTV = findViewById(R.id.tv_sqliteSample_contactsCount);
        showAllContactsBTN = findViewById(R.id.button_sqliteSample_showAllContacts);

        addContactBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameET.length() > 0) {
                    if (phoneNumberET.length() > 0) {

                    } else {
                        phoneNumberET.setError("Phone number is empty");
                    }
                } else {
                    nameET.setError("Name is empty");
                }
            }
        });

        showAllContactsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SQLiteSampleActivity.this, ContactsListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateContactsCount() {
        int count=0;
        contactsCountTV.setText(String.valueOf(count));
        if (count > 0) {
            showAllContactsBTN.setEnabled(true);
        } else {
            showAllContactsBTN.setEnabled(false);
        }
    }
}
