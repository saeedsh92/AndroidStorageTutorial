package com.ss.androidstoragesystemstutorial.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 1/7/2018.
 */

public class SQLiteHelper extends SQLiteOpenHelper implements ContactDAO {

    public SQLiteHelper(Context context) {
        super(context, "my_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE tbl_contacts (id INTEGER PRIMARY KEY," +
                    "name TEXT," +
                    "phone_number TEXT)");
        } catch (SQLiteException e) {
            Log.e("SQLITE", "onCreate: " + e.toString());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public boolean addContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", contact.getName());
        contentValues.put("phone_number", contact.getPhoneNumber());
        long result = db.insert("tbl_contacts", null, contentValues);
        db.close();
        return result != -1;
    }

    @Override
    public boolean removeContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();
        int result = db.delete("tbl_contacts", "id = ?", new String[]{String.valueOf(contact.getId())});
        db.close();
        return result != 0;
    }

    @Override
    public boolean updateContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", contact.getName());
        contentValues.put("phone_number", contact.getPhoneNumber());
        int result = db.update("tbl_contacts", contentValues, " id = ?", new String[]{String.valueOf(contact.getId())});
        db.close();
        return result != 0;
    }

    @Override
    public int getContactsCount() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tbl_contacts", null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    @Override
    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tbl_contacts", null);
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(cursor.getInt(0));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                contacts.add(contact);
            } while (cursor.moveToNext());
        }
        return contacts;
    }


}
