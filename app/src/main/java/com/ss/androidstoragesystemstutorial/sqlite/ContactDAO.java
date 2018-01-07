package com.ss.androidstoragesystemstutorial.sqlite;

import java.util.List;

/**
 * Created by Saeed shahini on 1/7/2018.
 */

public interface ContactDAO {

    boolean addContact(Contact contact);

    boolean removeContact(Contact contact);

    boolean updateContact(Contact contact);

    int getContactsCount();

    List<Contact> getAllContacts();

}
