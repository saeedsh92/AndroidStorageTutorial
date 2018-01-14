package com.ss.androidstoragesystemstutorial.filemanagement;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.ss.androidstoragesystemstutorial.R;

public class FileManagementActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSION = 2001;
    private String defaultPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_managment);
        defaultPath = Environment.getExternalStorageDirectory().getPath();
        Toolbar toolbar = findViewById(R.id.toolbar_fileManagement);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
                showPermissionRequestFragment();
            } else {
                showFileListFragment(defaultPath);
            }
        } else {
            showFileListFragment(defaultPath);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] >= 0) {
                hidePermssionRequestFragment();
                showFileListFragment(defaultPath);
            }
        }
    }

    private void showPermissionRequestFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_fileManagement_fragmentContainer, PermissionRequestFragment.newInstance("Storage"));
        fragmentTransaction.commit();
    }

    private void hidePermssionRequestFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_fileManagement_fragmentContainer);
        if (fragment != null && fragment instanceof PermissionRequestFragment) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        }
    }

    public void showFileListFragment(String path) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_fileManagement_fragmentContainer, FileListFragment.newInstance(path));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=new MenuInflater(this);
        menuInflater.inflate(R.menu.file_list_create_folder_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.menuItem_createFolder){
            CreateNewFolderDialog createNewFolderDialog=CreateNewFolderDialog.newInstance(new CreateNewFolderDialog.CreateFolderCallback() {
                @Override
                public void onCreateButtonClick(String folderName) {
                    Fragment fragment=getSupportFragmentManager().findFragmentById(R.id.frame_fileManagement_fragmentContainer);
                    if (fragment instanceof FileListFragment){
                        ((FileListFragment) fragment).createNewFolder(folderName);
                    }
                }
            });
            createNewFolderDialog.show(getSupportFragmentManager(),null);
        }
        return super.onOptionsItemSelected(item);
    }
}
