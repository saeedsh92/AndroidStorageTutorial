package com.ss.androidstoragesystemstutorial.filemanagement;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ss.androidstoragesystemstutorial.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saeed shahini on 1/12/2018.
 */

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileViewHolder> {
    private List<File> files = new ArrayList<>();
    private FileViewHolder.OnFileClick onFileClick;

    public FileAdapter(FileViewHolder.OnFileClick onFileClick) {
        this.onFileClick = onFileClick;
    }

    @Override
    public FileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FileViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_file, parent, false
        ), onFileClick);
    }

    @Override
    public void onBindViewHolder(FileViewHolder holder, int position) {
        holder.bindFile(files.get(position));
    }

    public void setFiles(List<File> files) {
        this.files = files;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public static class FileViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView fileNameTextView;
        private OnFileClick onFileClick;

        public FileViewHolder(View itemView, OnFileClick onFileClick) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_file);
            fileNameTextView = itemView.findViewById(R.id.tv_file_name);
            this.onFileClick = onFileClick;
        }

        public void bindFile(final File file) {
            if (file.isDirectory()) {
                imageView.setImageResource(R.drawable.ic_folder_white_24dp);
            } else {
                imageView.setImageResource(R.drawable.ic_file_white_24dp);
            }

            fileNameTextView.setText(file.getName());

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onFileClick.onFileClick(file);
                }
            });
        }

        public interface OnFileClick {
            void onFileClick(File file);
        }
    }
}
