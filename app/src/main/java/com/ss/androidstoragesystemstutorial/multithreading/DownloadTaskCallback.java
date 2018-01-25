package com.ss.androidstoragesystemstutorial.multithreading;

/**
 * Created by user on 1/21/2018.
 */

public interface DownloadTaskCallback {
    void onProgressUpdate(int progress);

    void onTaskFinish();
}
