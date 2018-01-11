package com.ss.androidstoragesystemstutorial.room;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ss.androidstoragesystemstutorial.DBInjector;
import com.ss.androidstoragesystemstutorial.R;
import com.ss.androidstoragesystemstutorial.sqlite.Contact;
import com.ss.androidstoragesystemstutorial.sqlite.ContactDAO;
import com.ss.androidstoragesystemstutorial.sqlite.ContactsListActivity;
import com.ss.androidstoragesystemstutorial.sqlite.SQLiteSampleActivity;
import com.ss.androidstoragesystemstutorial.util.Util;

public class RoomSampleActivity extends AppCompatActivity {

    private EditText nameET;
    private EditText phoneNumberET;
    private Button addContactBTN;
    private View showAllContactsBTN;
    private TextView contactsCountTV;
    private ContactDAO contactDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_sample);
        setupViews();
        contactDAO= DBInjector.provideContactDao(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                        Contact contact=new Contact();
                        contact.setName(nameET.getText().toString());
                        contact.setPhoneNumber(phoneNumberET.getText().toString());
                        if (contactDAO.addContact(contact)>0){
                            Toast.makeText(RoomSampleActivity.this,"SUCCESS",Toast.LENGTH_SHORT).show();
                            clearEditTexts();
                            Util.closeKeyboard(RoomSampleActivity.this);
                            updateContactsCount();
                        }else {
                            Toast.makeText(RoomSampleActivity.this,"FAILED",Toast.LENGTH_SHORT).show();
                        }

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
                Intent intent = new Intent(RoomSampleActivity.this, ContactsListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateContactsCount() {
        int count = contactDAO.getContactsCount();
        contactsCountTV.setText(String.valueOf(count));
        if (count > 0) {
            showAllContactsBTN.setEnabled(true);
        } else {
            showAllContactsBTN.setEnabled(false);
        }
    }

    private void clearEditTexts(){
        nameET.setText("");
        phoneNumberET.setText("");
    }
}
