package com.ss.androidstoragesystemstutorial.services;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ss.androidstoragesystemstutorial.R;

public class IntentServiceSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service_sample);
        Toolbar toolbar = findViewById(R.id.toolbar_intentServiceSample);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final EditText inputUrlEditText = findViewById(R.id.et_intentService_inputUrl);
        Button downloadFileButton = findViewById(R.id.button_intentService_inputUrl);
        downloadFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputUrlEditText.length() > 0) {
                    Intent intent = new Intent(IntentServiceSampleActivity.this, DownloadFileService.class);
                    intent.putExtra(DownloadFileService.EXTRA_KEY_URL, inputUrlEditText.getText().toString());
                    startService(intent);
                }
            }
        });
    }
}
