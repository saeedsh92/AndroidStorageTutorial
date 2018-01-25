package com.ss.androidstoragesystemstutorial.multithreading;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by user on 1/21/2018.
 */

public class ThreadSample extends Thread {
    private static final String TAG = "ThreadSample";
    private DownloadTaskCallback downloadTaskCallback;
    private Handler handler;

    public ThreadSample(Handler handler, DownloadTaskCallback downloadTaskCallback) {
        this.downloadTaskCallback = downloadTaskCallback;
        this.handler = handler;
    }

    @Override
    public void run() {
        super.run();
        downloadLargeFile();
    }

    private void downloadLargeFile() {
        //Heavy Task: downloading file from server
        try {
            for (int i = 0; i < 100; i++) {
                Thread.sleep(500);
                downloadTaskCallback.onProgressUpdate(i);
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    downloadTaskCallback.onTaskFinish();
                }
            });
            Log.d(TAG, "downloadLargeFile() called");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
