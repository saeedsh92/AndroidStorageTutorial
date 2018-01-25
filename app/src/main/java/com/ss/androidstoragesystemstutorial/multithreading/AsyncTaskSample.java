package com.ss.androidstoragesystemstutorial.multithreading;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by user on 1/21/2018.
 */

public class AsyncTaskSample extends AsyncTask<String, Integer, Void> {
    private static final String TAG = "AsyncTaskSample";
    private DownloadTaskCallback downloadTaskCallback;

    public AsyncTaskSample(DownloadTaskCallback downloadTaskCallback) {
        this.downloadTaskCallback = downloadTaskCallback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... strings) {
        if (isCancelled()){

        }
        String url = strings[0];
        Log.i(TAG, "doInBackground: url=> " + url);
        try {
            for (int i = 0; i < 100; i++) {
                Thread.sleep(500);
                publishProgress(i);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        downloadTaskCallback.onTaskFinish();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        downloadTaskCallback.onProgressUpdate(values[0]);
    }
}
