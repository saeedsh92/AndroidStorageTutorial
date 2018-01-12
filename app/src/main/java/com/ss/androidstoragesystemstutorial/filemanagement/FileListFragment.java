package com.ss.androidstoragesystemstutorial.filemanagement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ss.androidstoragesystemstutorial.R;

import java.io.File;

/**
 * Created by Saeed shahini on 1/12/2018.
 */

public class FileListFragment extends Fragment implements FileAdapter.FileViewHolder.OnFileClick {
    private static final String EXTRA_KEY_PATH = "path";
    private View rootView;
    private RecyclerView recyclerView;
    private FileAdapter fileAdapter = new FileAdapter(this);

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
        recyclerView.setAdapter(fileAdapter);
    }

    public static FileListFragment newInstance(String path) {

        Bundle args = new Bundle();
        args.putString("path", path);
        FileListFragment fragment = new FileListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onFileClick(File file) {

    }

    public FileManagementActivity getParentActivity(){
        return (FileManagementActivity) getActivity();
    }
}
