package com.ss.androidstoragesystemstutorial.sqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.ss.androidstoragesystemstutorial.DBInjector;
import com.ss.androidstoragesystemstutorial.R;

import java.util.ArrayList;

public class ContactsListActivity extends AppCompatActivity implements ContactAdapter.ContactViewHolder.ContactViewCallback {
    private ContactDAO contactDAO;
    private ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);
        contactDAO = DBInjector.provideContactDao(this);

        Toolbar toolbar = findViewById(R.id.toolbar_contactsList);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        RecyclerView recyclerView = findViewById(R.id.rv_contactsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        contactAdapter = new ContactAdapter(contactDAO.getAllContacts(), this);
        recyclerView.setAdapter(contactAdapter);
    }

    @Override
    public void onDeleteClick(Contact contact) {
        if (contactDAO.removeContact(contact)) {
            Toast.makeText(this,"SUCCESS",Toast.LENGTH_SHORT).show();
            contactAdapter.notifyDataSetChanged();
        }else {
            Toast.makeText(this,"FAILED",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onEditClick(Contact contact) {
        EditContactDialog.newInstance(contact).show(getSupportFragmentManager(), null);
    }
}
