package com.ss.androidstoragesystemstutorial.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ss.androidstoragesystemstutorial.sqlite.Contact;
import com.ss.androidstoragesystemstutorial.sqlite.ContactDAO;

/**
 * Created by user on 1/11/2018.
 */
@Database(entities = {Contact.class}, version = 1,exportSchema = false)
public abstract class MyRoomDatabase extends RoomDatabase {
    private static MyRoomDatabase instance;

    public abstract ContactDAO contactDAO();

    public static MyRoomDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, MyRoomDatabase.class, "my_db").allowMainThreadQueries().build();
        }

        return instance;
    }
}
