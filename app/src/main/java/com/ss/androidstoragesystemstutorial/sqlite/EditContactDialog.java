package com.ss.androidstoragesystemstutorial.sqlite;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ss.androidstoragesystemstutorial.DBInjector;
import com.ss.androidstoragesystemstutorial.R;
import com.ss.androidstoragesystemstutorial.util.Util;

/**
 * Created by Saeed shahini on 1/7/2018.
 */

public class EditContactDialog extends DialogFragment {
    private static final String EXTRA_KEY_CONTACT = "contact";
    private ContactDAO contactDAO;
    private UpdateContactCallback updateContactCallback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contactDAO = DBInjector.provideContactDao(getContext());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_contact, null, false);
        setupViews(view);
        builder.setView(view);
        return builder.create();
    }

    private void setupViews(View view) {
        final EditText nameEt = view.findViewById(R.id.et_editContact_name);
        final EditText phoneNumberET = view.findViewById(R.id.et_editContact_phoneNumber);
        Button updateContact = view.findViewById(R.id.button_editContact_updateContact);
        updateContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameEt.length() > 0) {
                    if (phoneNumberET.length() > 0) {
                        Contact contact = getArguments().getParcelable(EXTRA_KEY_CONTACT);
                        contact.setName(nameEt.getText().toString());
                        contact.setPhoneNumber(phoneNumberET.getText().toString());
                        if (contactDAO.updateContact(contact)>0) {
                            Util.closeKeyboard(getActivity());
                            Toast.makeText(getContext(), "SUCCESS", Toast.LENGTH_SHORT).show();
                            dismiss();
                        } else {
                            Toast.makeText(getContext(), "FAILED", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        phoneNumberET.setError("Phone number is empty");
                    }
                } else {
                    nameEt.setError("Name is empty");
                }
            }
        });
    }

    public static EditContactDialog newInstance(Contact contact) {

        Bundle args = new Bundle();
        args.putParcelable(EXTRA_KEY_CONTACT, contact);
        EditContactDialog fragment = new EditContactDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public void setUpdateContactCallback(UpdateContactCallback updateContactCallback) {

        this.updateContactCallback = updateContactCallback;
    }

    public interface UpdateContactCallback {
        void onUpdate();
    }
}
