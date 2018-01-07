package com.ss.androidstoragesystemstutorial;

import android.content.Context;

import com.ss.androidstoragesystemstutorial.sqlite.ContactDAO;
import com.ss.androidstoragesystemstutorial.sqlite.SQLiteHelper;

/**
 * Created by user on 1/7/2018.
 */

public class DBInjector {
    public static ContactDAO provideContactDao(Context context) {
        return new SQLiteHelper(context.getApplicationContext());
    }
}
