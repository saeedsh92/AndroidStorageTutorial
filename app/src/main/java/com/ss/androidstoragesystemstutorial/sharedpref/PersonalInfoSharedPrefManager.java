package com.ss.androidstoragesystemstutorial.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by user on 1/7/2018.
 */

public class PersonalInfoSharedPrefManager {
    private static final String EXTRA_KEY_FIRST_NAME="first_name";
    private static final String EXTRA_KEY_LAST_NAME="last_name";
    private static final String EXTRA_KEY_AGE="age";
    private static final String EXTRA_KEY_IS_ANDROID_EXPERT="is_android_expert";
    private SharedPreferences sharedPreferences;

    public PersonalInfoSharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences("personal_info_shp", Context.MODE_PRIVATE);
    }

    public void savePersonalInfo(String firstName, String lastName, int age, boolean isAndroidExpertStudent) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EXTRA_KEY_FIRST_NAME,firstName);
        editor.putString(EXTRA_KEY_LAST_NAME,lastName);
        editor.putInt(EXTRA_KEY_AGE,age);
        editor.putBoolean(EXTRA_KEY_IS_ANDROID_EXPERT,isAndroidExpertStudent);
        editor.commit();
    }

    public String getFirstName() {
        return sharedPreferences.getString(EXTRA_KEY_FIRST_NAME,"");
    }

    public String getLastName() {
        return sharedPreferences.getString(EXTRA_KEY_LAST_NAME,"");
    }

    public int getAge() {
        return sharedPreferences.getInt(EXTRA_KEY_AGE,18);
    }

    public boolean getIsAndroidExpertStudent() {
        return sharedPreferences.getBoolean(EXTRA_KEY_IS_ANDROID_EXPERT,false);
    }
}
