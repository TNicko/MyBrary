package com.example.mybrary.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybrary.R;
import com.example.mybrary.domain.model.Folder;
import com.example.mybrary.ui.view.FolderActivity;

import java.util.ArrayList;
import java.util.List;

public class FolderRecViewAdapter extends RecyclerView.Adapter<FolderRecViewAdapter.ViewHolder> {

    private List<Folder> folders = new ArrayList<>();
    private ArrayList<String> wordCount = new ArrayList<>();
//    private ArrayList<String> reviewCount = new ArrayList<>();
    private final Context context;
    private long word_num;

    public FolderRecViewAdapter (Context context) {
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.folder_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Set Folder Name
        holder.txtName.setText(folders.get(position).getName());
        // Set Word Count
//        holder.txtWordNum.setText(wordCount.get(position));
        // !!! GetReviewCount() for each folder !!!

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView folderName1 = view.findViewById(R.id.txtName);
                String folderName = folderName1.getText().toString();
                folderActivity(folderName);
            }
        });
    }

    // Switch Activity -> FolderActivity
    public void folderActivity(String folderName) {
        Intent intent = new Intent(context, FolderActivity.class);
        intent.putExtra("FOLDER_NAME", folderName);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return folders.size();
    }

    public void setFolders(List<Folder> folders) {
        this.folders = folders;
        notifyDataSetChanged();
    }

    public void setWordCount(ArrayList<String> wordCount) {
        this.wordCount = wordCount;
    }

//    public void setReviewCount(String reviewCount) {
//        this.reviewCount = reviewCount;
//    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView txtName, txtWordNum, txtReviewNum;
        private final CardView parent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            parent = itemView.findViewById(R.id.parent);
            txtWordNum = itemView.findViewById(R.id.txtWordNum);
            txtReviewNum = itemView.findViewById(R.id.txtReviewNum);
        }
    }
}
