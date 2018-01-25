package com.ss.androidstoragesystemstutorial.services;

import android.accounts.NetworkErrorException;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.ss.androidstoragesystemstutorial.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by user on 1/25/2018.
 */

public class DownloadFileService extends IntentService {
    private static final String TAG = "DownloadFileService";
    public static final String EXTRA_KEY_URL = "file_url";
    private NotificationManager notificationManager;
    private static final int NOTIFICATION_ID = 1001;
    private String fileName;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DownloadFileService(String name) {
        super(name);
    }

    public DownloadFileService() {
        super("Default");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind() called with: intent = [" + intent + "]");
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent() called with: intent = [" + intent + "]");
        //Start Downloading file
        if (intent != null) {
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            startForeground(NOTIFICATION_ID,updateNotification("Download File","Downloading is in progress",-1));
            String url = intent.getStringExtra(EXTRA_KEY_URL);
            downloadingFile(url);
        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy() called");
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate() called");
        super.onCreate();
    }

    private void downloadingFile(String urlString) {
        fileName = urlString.substring(urlString.lastIndexOf("/"));
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                onError(new Exception("Cannot create download directory"));
                return;
            }
        }

        InputStream inputStream;
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream(directory.getPath() + "/" + fileName);
            try {

                updateNotification("Download " + fileName, "Connecting To Server", -1);
                URL url = new URL(urlString);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    onError(new NetworkErrorException());
                    return;
                }
                inputStream = httpURLConnection.getInputStream();
                int fileTotalBytes = httpURLConnection.getContentLength();
                byte[] buffer = new byte[1024];
                int len;
                int downloadedBytes = 0;
                while ((len = inputStream.read(buffer)) > 0) {
                    downloadedBytes += len;
                    outputStream.write(buffer, 0, len);
                    updateNotification((downloadedBytes * 100) / fileTotalBytes);
                }
                updateNotification("Download " + fileName, "Downloading Complete", -1);
                stopForeground(false);
            } catch (MalformedURLException e) {
                onError(e);
            } catch (IOException e) {
                onError(e);
            }
        } catch (FileNotFoundException e) {
            onError(e);
        }

    }

    private Notification updateNotification(String title, String description, int progress) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(title)
                .setContentText(description)
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setProgress(progress == -1 ? 0 : 100, progress, false);
        Notification notification = builder.build();
        notificationManager.notify(NOTIFICATION_ID, notification);
        return notification;
    }

    private Notification updateNotification(int progress) {
        return updateNotification("Download " + fileName, "Downloading Is In Progress", progress);
    }

    private void onError(Exception e) {
        Log.d(TAG, "onError() called with: e = [" + e + "]");
        updateNotification("Download " + fileName, "Downloading File Failed", -1);
        stopForeground(false);
    }
}
