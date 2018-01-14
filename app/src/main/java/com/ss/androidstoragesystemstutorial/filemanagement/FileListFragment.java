package com.ss.androidstoragesystemstutorial.filemanagement;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ss.androidstoragesystemstutorial.R;
import com.ss.androidstoragesystemstutorial.sqlite.Contact;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Saeed shahini on 1/12/2018.
 */

public class FileListFragment extends Fragment implements FileAdapter.FileViewHolder.OnFileClick {
    private static final String EXTRA_KEY_PATH = "path";
    private View rootView;
    private RecyclerView recyclerView;
    private FileAdapter fileAdapter = new FileAdapter(this);
    private View emptyState;
    private String path;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        path = getArguments().getString(EXTRA_KEY_PATH);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_files_list, container, false);
            setupViews();
        }
        return rootView;
    }

    private void setupViews() {
        recyclerView = rootView.findViewById(R.id.rv_filesList);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false));
        emptyState = rootView.findViewById(R.id.ll_filesList_emptyStateContainer);
        File file = new File(path);
        File[] files = file.listFiles();
        List<File> fileListArray = new ArrayList<>(Arrays.asList(files));
        if (fileListArray.isEmpty()) {
            emptyState.setVisibility(View.VISIBLE);
        }
        recyclerView.setAdapter(fileAdapter);
        fileAdapter.setFiles(fileListArray);

    }

    public static FileListFragment newInstance(String path) {

        Bundle args = new Bundle();
        args.putString(EXTRA_KEY_PATH, path);
        FileListFragment fragment = new FileListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onFileClick(File file) {
        if (file.isDirectory())
            getParentActivity().showFileListFragment(path + "/" + file.getName());
    }

    @Override
    public void onFileLongClick(final File file) {
        FileActionsBottomSheet.newInstance(new FileActionsBottomSheet.ActionListener() {
            @Override
            public void onMoveButtonClick() {
                move(file);
            }

            @Override
            public void onCopyButtonClick() {
                copy(file);
            }

            @Override
            public void onDeleteButtonClick() {
                if (file.delete()) {
                    fileAdapter.removeFile(file);
                    Toast.makeText(getActivity(), "SUCCESS", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "FAILED", Toast.LENGTH_SHORT).show();
                }
            }
        }).show(getActivity().getSupportFragmentManager(), null);
    }

    public void createNewFolder(String folderName) {
        File newFolder = new File(path + "/" + folderName);
        if (!newFolder.exists()) {
            if (newFolder.mkdir()) {
                fileAdapter.addFile(newFolder);
                recyclerView.smoothScrollToPosition(0);
                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "FAILED", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "IS EXIST", Toast.LENGTH_SHORT).show();
        }
    }

    public FileManagementActivity getParentActivity() {
        return (FileManagementActivity) getActivity();
    }

    private void copy(File sourceFile) {
        File destinationFile = new File(Environment.getExternalStorageDirectory().getPath() + "/" + "SevenLearn/" + sourceFile.getName());
        if (!destinationFile.exists()) {

        }

        try {
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            FileOutputStream fileOutputStream = new FileOutputStream(destinationFile);
            try {
                byte[] buffer = new byte[1024];
                int len;
                while ((len = fileInputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, len);
                }

            } finally {
                fileInputStream.close();
                fileOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void move(File sourceFile) {
        copy(sourceFile);
        if (sourceFile.delete()) {
            Toast.makeText(getActivity(), "SUCCESS", Toast.LENGTH_SHORT).show();
        }
    }
}
