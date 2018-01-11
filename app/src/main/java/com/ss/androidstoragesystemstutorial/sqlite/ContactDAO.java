package com.ss.androidstoragesystemstutorial.sqlite;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Saeed shahini on 1/7/2018.
 */
@Dao
public interface ContactDAO {

    @Insert
    long addContact(Contact contact);

    @Delete
    int removeContact(Contact contact);

    @Update
    int updateContact(Contact contact);

    @Query("SELECT COUNT(*) FROM tbl_contacts")
    int getContactsCount();

    @Query("SELECT * FROM tbl_contacts")
    List<Contact> getAllContacts();

}
