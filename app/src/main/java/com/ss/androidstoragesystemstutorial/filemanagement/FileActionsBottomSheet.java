package com.ss.androidstoragesystemstutorial.filemanagement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ss.androidstoragesystemstutorial.R;

/**
 * Created by Saeed shahini on 1/12/2018.
 */

public class FileActionsBottomSheet extends BottomSheetDialogFragment {
    private ActionListener actionListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.bottomsheet_actions, null, false);
        View moveButton = view.findViewById(R.id.rl_fileActions_moveButton);
        View copyButton = view.findViewById(R.id.rl_fileActions_copyButton);
        View delete = view.findViewById(R.id.rl_fileActions_deleteButton);

        moveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                actionListener.onMoveButtonClick();
            }
        });

        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                actionListener.onCopyButtonClick();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                actionListener.onDeleteButtonClick();
            }
        });

        return view;
    }

    private void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    public static FileActionsBottomSheet newInstance(ActionListener actionListener) {
        FileActionsBottomSheet fragment = new FileActionsBottomSheet();
        fragment.setActionListener(actionListener);
        return fragment;
    }


    public interface ActionListener {
        void onMoveButtonClick();

        void onCopyButtonClick();

        void onDeleteButtonClick();
    }

}
