package com.example.mybrary.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybrary.R;
import com.example.mybrary.domain.model.Review;
import com.example.mybrary.domain.model.Word;
import com.example.mybrary.ui.view.UpdateWordActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WordRecViewAdapter extends RecyclerView.Adapter<WordRecViewAdapter.ViewHolder>{

    private List<Word> words = new ArrayList<>();
    private List<Review> reviews = new ArrayList<>();
    private final Context context;
    private String folderName;

    public WordRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String folderId = words.get(position).getFolder_id();
        // Set info for recycle view
        holder.parent.setTag(words.get(position).getId());
        holder.txtName.setText(words.get(position).getWord());
        holder.txtTranslation.setText(words.get(position).getTranslation());

        if (words.get(position).isReview()) {
            holder.statusBar.setVisibility(View.VISIBLE);
            holder.statusBarNo.setVisibility(View.VISIBLE);
            List<Review> review = reviews.stream()
                    .filter(item -> item.getWordId().equals(words.get(position).getId()))
                    .collect(Collectors.toList());
            if (review.size() > 0){
                long level = review.get(0).getLevel();
                int levelPercent = (int) (level * 5);
                holder.statusBar.setProgress(levelPercent);
                holder.statusBarNo.setText(String.valueOf(level));
            }

        } else {
            holder.statusBar.setVisibility(View.INVISIBLE);
            holder.statusBarNo.setVisibility(View.INVISIBLE);
        }

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wordId = (String) holder.parent.getTag();
                updateWordActivity(wordId, folderId, folderName);
            }
        });
    }

    // Switch Activity -> updateWordActivity
    public void updateWordActivity(String wordId, String folderId, String folderName) {
        Intent intent = new Intent(context, UpdateWordActivity.class);
        intent.putExtra("WORD_ID", wordId);
        intent.putExtra("FOLDER_ID", folderId);
        intent.putExtra("FOLDER_NAME", folderName);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    public void setData(List<Word> words, List<Review> reviews, String folderName) {
        this.words = words;
        this.reviews = reviews;
        this.folderName = folderName;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final ConstraintLayout parent;
        private final TextView txtName, txtTranslation, statusBarNo;
        ProgressBar statusBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            txtName = itemView.findViewById(R.id.txtName);
            txtTranslation = itemView.findViewById(R.id.txtTranslation);
            statusBar = itemView.findViewById(R.id.statusBar);
            statusBarNo = itemView.findViewById(R.id.statusBarNo);
        }
    }
}
