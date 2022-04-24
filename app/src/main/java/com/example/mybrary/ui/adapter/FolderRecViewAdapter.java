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
import com.example.mybrary.domain.model.Word;
import com.example.mybrary.ui.view.FolderActivity;

import java.util.ArrayList;
import java.util.List;

public class FolderRecViewAdapter extends RecyclerView.Adapter<FolderRecViewAdapter.ViewHolder> {

    private List<Folder> folders = new ArrayList<>();
    private List<Word> words = new ArrayList<>();
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
        // Set info for recycle view
        holder.txtName.setText(folders.get(position).getName());
        holder.parent.setTag(folders.get(position).getId());
        int wordCount = 0;
        // Number of words in current folder
        for(Word word : words) {
            if(word.getFolder_id() == folders.get(position).getId()) {
                wordCount += 1;
            }
        }
        String wordNum = String.valueOf(wordCount);
        holder.txtWordNum.setText(wordNum);

        // !!! GetReviewCount() for each folder !!!

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long folderId = (long) holder.parent.getTag();
                folderActivity(folderId);
            }
        });
    }

    // Switch Activity -> FolderActivity
    public void folderActivity(long folderId) {
        Intent intent = new Intent(context, FolderActivity.class);
        intent.putExtra("FOLDER_ID", folderId);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return folders.size();
    }

    public void setData(List<Folder> folders, List<Word> words) {
        this.folders = folders;
        this.words = words;
        notifyDataSetChanged();
    }

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
