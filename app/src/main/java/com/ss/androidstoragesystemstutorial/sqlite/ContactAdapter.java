package com.ss.androidstoragesystemstutorial.sqlite;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ss.androidstoragesystemstutorial.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saeed shahini on 1/7/2018.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private List<Contact> contacts = new ArrayList<>();
    private ContactViewHolder.ContactViewCallback contactViewCallback;

    public ContactAdapter(List<Contact> contacts, ContactViewHolder.ContactViewCallback contactViewCallback) {
        this.contacts = contacts;
        this.contactViewCallback = contactViewCallback;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false), contactViewCallback);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        holder.bindContact(contacts.get(position));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        private ContactViewCallback contactViewCallback;

        private TextView nameTextView;
        private TextView phoneNumberTextView;
        private TextView letterTextView;
        private ImageView editImageView;
        private ImageView deleteImageView;

        public ContactViewHolder(View itemView, ContactViewCallback contactViewCallback) {
            super(itemView);
            this.contactViewCallback = contactViewCallback;

            nameTextView = itemView.findViewById(R.id.tv_contact_name);
            phoneNumberTextView = itemView.findViewById(R.id.tv_contact_phoneNumber);
            letterTextView = itemView.findViewById(R.id.tv_contact_letter);
            deleteImageView = itemView.findViewById(R.id.iv_contact_delete);
            editImageView = itemView.findViewById(R.id.iv_contact_edit);
        }

        public void bindContact(final Contact contact) {
            nameTextView.setText(contact.getName());
            phoneNumberTextView.setText(contact.getPhoneNumber());
            letterTextView.setText(contact.getName().charAt(0));
            deleteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    contactViewCallback.onDeleteClick(contact);
                }
            });

            editImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    contactViewCallback.onEditClick(contact);
                }
            });
        }

        public interface ContactViewCallback {
            void onDeleteClick(Contact contact);

            void onEditClick(Contact contact);
        }
    }
}
