package com.ss.androidstoragesystemstutorial.multithreading;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.ss.androidstoragesystemstutorial.R;

public class MultiThreadingSampleActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_threading_sample);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_multiThreadingSample_asyncTask:
                final ProgressDialog progressDialog = new ProgressDialog(MultiThreadingSampleActivity.this);
                progressDialog.setTitle("Download file using async task");
                progressDialog.setIndeterminate(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                AsyncTaskSample asyncTaskSample = new AsyncTaskSample(new DownloadTaskCallback() {
                    @Override
                    public void onProgressUpdate(int progress) {
                        progressDialog.setProgress(progress);
                    }

                    @Override
                    public void onTaskFinish() {
                        Toast.makeText(MultiThreadingSampleActivity.this, "Download Completed", Toast.LENGTH_SHORT).show();
                    }
                });
                asyncTaskSample.execute("http://7learn.com");
                asyncTaskSample.cancel(true);
                progressDialog.show();
                break;
            case R.id.button_multiThreadingSample_thread:
                final ProgressDialog progressDialog2 = new ProgressDialog(this);
                progressDialog2.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog2.setIndeterminate(false);
                progressDialog2.setTitle("Download File");
                Handler handler = new Handler(Looper.getMainLooper());
                ThreadSample threadSample = new ThreadSample(handler, new DownloadTaskCallback() {
                    @Override
                    public void onProgressUpdate(int progress) {
                        progressDialog2.setProgress(progress);
                    }

                    @Override
                    public void onTaskFinish() {
                        Toast.makeText(MultiThreadingSampleActivity.this, "Download Completed", Toast.LENGTH_SHORT).show();
                    }
                });
                progressDialog2.show();
                threadSample.start();
                break;
        }
    }
}
