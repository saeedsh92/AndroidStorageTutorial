package com.ss.androidstoragesystemstutorial.filemanagement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ss.androidstoragesystemstutorial.R;

/**
 * Created by Saeed shahini on 1/12/2018.
 */

public class PermissionRequestFragment extends Fragment {
    private static final String EXTRA_KEY_PERMISSION = "permission_name";
    private View rootLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootLayout == null) {
            rootLayout = inflater.inflate(R.layout.fragment_permission_required, container, false);
            setupViews();
        }
        return rootLayout;
    }

    private void setupViews() {
        TextView messageTextView=rootLayout.findViewById(R.id.tv_permissionRequest_message);
        messageTextView.setText(getString(R.string.permissionRequest_message,getArguments().getString(EXTRA_KEY_PERMISSION)));
    }

    public static PermissionRequestFragment newInstance(String permissionName) {

        Bundle args = new Bundle();
        args.putString(EXTRA_KEY_PERMISSION, permissionName);
        PermissionRequestFragment fragment = new PermissionRequestFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
