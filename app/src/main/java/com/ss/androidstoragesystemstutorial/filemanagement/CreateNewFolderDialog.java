package com.ss.androidstoragesystemstutorial.filemanagement;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ss.androidstoragesystemstutorial.R;

/**
 * Created by Saeed shahini on 1/12/2018.
 */

public class CreateNewFolderDialog extends DialogFragment {
    private CreateFolderCallback createFolderCallback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_create_folder, null, false);
        final EditText editText = view.findViewById(R.id.et_createFolder_name);
        final Button createFolderButton = view.findViewById(R.id.button_createFolder_create);
        createFolderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.length() > 0) {
                    createFolderCallback.onCreateButtonClick(editText.getText().toString());
                    dismiss();
                } else {
                    editText.setError("Name is empty");
                }
            }
        });
        builder.setView(view);
        return builder.create();
    }


    private void setCreateFolderCallback(CreateFolderCallback createFolderCallback) {
        this.createFolderCallback = createFolderCallback;
    }

    public static CreateNewFolderDialog newInstance(CreateFolderCallback createFolderCallback) {
        CreateNewFolderDialog fragment = new CreateNewFolderDialog();
        fragment.setCreateFolderCallback(createFolderCallback);
        return fragment;
    }

    public interface CreateFolderCallback {
        void onCreateButtonClick(String folderName);
    }
}
