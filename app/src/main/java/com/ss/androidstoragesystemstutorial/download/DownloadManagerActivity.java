package com.ss.androidstoragesystemstutorial.download;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ss.androidstoragesystemstutorial.R;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import static android.view.View.GONE;

public class DownloadManagerActivity extends AppCompatActivity {
    private EditText urlEditText;
    private Button downloadButton;
    private TextView statusTextView;
    private TextView percentTextView;
    private ProgressBar progressBar;
    private DownloadManager downloadManager;
    private long downloadId;
    private Timer timer;
    private boolean isDownloading;
    private DownloadFinishedBroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_manager);
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

        setupViews();
    }

    private void setupViews() {
        Toolbar toolbar = findViewById(R.id.toolbar_downloadManager);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        urlEditText = findViewById(R.id.et_downloadManager_urlInput);
        downloadButton = findViewById(R.id.button_downloadManger_download);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDownloading) {
                    downloadManager.remove(downloadId);
                } else {
                    if (urlEditText.length() > 0) {
                        try {
                            String url = urlEditText.getText().toString();
                            String fileName = url.substring(url.lastIndexOf("/"));
                            Uri urlUri = Uri.parse(url);
                            File file = new File(Environment.getExternalStorageDirectory().getPath() + "/7Learn/" + fileName);
                            Uri fileUri = Uri.fromFile(file);
                            Toast.makeText(DownloadManagerActivity.this, fileUri.toString(), Toast.LENGTH_LONG).show();
                            DownloadManager.Request request = new DownloadManager.Request(urlUri);
                            request.setTitle("Downloading " + fileName);
                            request.setDescription("Downloading file with android DownloadManager");
                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                                    DownloadManager.Request.NETWORK_MOBILE);
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
                            downloadId = downloadManager.enqueue(request);
                            onDownloadStarted();
                        } catch (StringIndexOutOfBoundsException e) {
                            urlEditText.setError("Url isn't Valid");
                        }

                    } else {
                        urlEditText.setError("Url is Empty");
                    }
                }

            }
        });
        statusTextView = findViewById(R.id.tv_downloadManager_status);
        progressBar = findViewById(R.id.progressBar_downloadManager);
        percentTextView = findViewById(R.id.tv_downloadManager_percent);
    }

    private void onDownloadStarted() {
        isDownloading = true;
        downloadButton.setText(getString(R.string.downloadManager_cancel));
        progressBar.setVisibility(View.VISIBLE);
        statusTextView.setVisibility(View.VISIBLE);
        percentTextView.setVisibility(View.VISIBLE);
        timer = new Timer();
        timer.schedule(new CheckDownloadStatusTimerTask(), 0, 500);
    }

    private void onDownloadFinished() {
        isDownloading = false;
        progressBar.setVisibility(GONE);
        statusTextView.setVisibility(GONE);
        percentTextView.setVisibility(GONE);
        progressBar.setProgress(0);
        timer.purge();
        timer.cancel();
        downloadButton.setText(getString(R.string.downloadManager_download));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (broadcastReceiver == null) {
            broadcastReceiver = new DownloadFinishedBroadcastReceiver();
        }

        registerReceiver(broadcastReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
        timer.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.purge();
        timer.cancel();
    }

    private class CheckDownloadStatusTimerTask extends TimerTask {
        @Override
        public void run() {
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(downloadId, 2);
            Cursor cursor = downloadManager.query(query);
            if (cursor.moveToFirst()) {
                long downloadedBytes = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                long totalBytes = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                final int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                final int percent = (int) ((downloadedBytes * 100L) / totalBytes);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        percentTextView.setText(getString(R.string.downloadManager_downloadFilePercent, percent));
                        progressBar.setProgress(percent);
                        switch (status) {
                            case DownloadManager.STATUS_RUNNING:
                                statusTextView.setText(getString(R.string.downloadManager_status, "Running"));
                                break;
                            case DownloadManager.STATUS_PENDING:
                                statusTextView.setText(getString(R.string.downloadManager_status, "Pending"));
                                break;
                            case DownloadManager.STATUS_FAILED:
                                statusTextView.setText(getString(R.string.downloadManager_status, "Failed"));
                                break;
                            case DownloadManager.STATUS_PAUSED:
                                statusTextView.setText(getString(R.string.downloadManager_status, "Paused"));
                                break;
                            case DownloadManager.STATUS_SUCCESSFUL:
                                statusTextView.setText(getString(R.string.downloadManager_status, "Successful"));
                                break;
                        }
                    }
                });
            }
        }
    }

    private class DownloadFinishedBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            onDownloadFinished();
        }
    }
}
