package com.ss.androidstoragesystemstutorial;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;

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
                break;
            case R.id.tv_main_githubLink:
                startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/saeedsh92/AndroidStorageTutorial")),
                        "Select Browser"));
                break;
        }
    }
}
