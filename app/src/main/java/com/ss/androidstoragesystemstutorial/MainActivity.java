package com.ss.androidstoragesystemstutorial;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;

import com.ss.androidstoragesystemstutorial.broadcast.BroadcastReceiverSampleActivity;
import com.ss.androidstoragesystemstutorial.download.DownloadManagerActivity;
import com.ss.androidstoragesystemstutorial.filemanagement.FileManagementActivity;
import com.ss.androidstoragesystemstutorial.multithreading.MultiThreadingSampleActivity;
import com.ss.androidstoragesystemstutorial.room.RoomSampleActivity;
import com.ss.androidstoragesystemstutorial.services.IntentServiceSampleActivity;
import com.ss.androidstoragesystemstutorial.sharedpref.SharedPreferenceSampleActivity;
import com.ss.androidstoragesystemstutorial.sqlite.SQLiteSampleActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
    }

    private void setupViews() {
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.post(new Runnable() {
            @Override
            public void run() {

            }
        });
        setSupportActionBar(toolbar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_main_sharedPref:
                startActivity(new Intent(this, SharedPreferenceSampleActivity.class));
                break;
            case R.id.button_main_sqlite:
                startActivity(new Intent(this, SQLiteSampleActivity.class));
                break;
            case R.id.button_main_room:
                startActivity(new Intent(this, RoomSampleActivity.class));
                break;
            case R.id.tv_main_githubLink:
                startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/saeedsh92/AndroidStorageTutorial")),
                        "Select Browser"));
                break;
            case R.id.button_main_fileManagement:
                startActivity(new Intent(this, FileManagementActivity.class));
                break;
            case R.id.button_main_downloadManager:
                startActivity(new Intent(this, DownloadManagerActivity.class));
                break;
            case R.id.button_main_multiThreadingSample:
                startActivity(new Intent(this, MultiThreadingSampleActivity.class));
                break;

            case R.id.button_main_services:
                startActivity(new Intent(this, IntentServiceSampleActivity.class));
                break;
            case R.id.button_main_broadCastReceiver:
                startActivity(new Intent(this, BroadcastReceiverSampleActivity.class));
                break;
        }
    }
}
