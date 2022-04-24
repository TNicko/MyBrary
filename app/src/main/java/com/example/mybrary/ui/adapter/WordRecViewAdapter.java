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

public class WordRecViewAdapter extends RecyclerView.Adapter<WordRecViewAdapter.ViewHolder>{

    private List<Word> words = new ArrayList<>();
    private List<Review> reviews = new ArrayList<>();
    private final Context context;

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
        long folderId = words.get(position).getFolder_id();
        // Set info for recycle view
        holder.parent.setTag(words.get(position).getId());
        holder.txtName.setText(words.get(position).getWord());
        holder.txtTranslation.setText(words.get(position).getTranslation());

        if (words.get(position).isReview()) {
            holder.statusBar.setVisibility(View.VISIBLE);
        } else {
            holder.statusBar.setVisibility(View.INVISIBLE);
        }

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long wordId = (long) holder.parent.getTag();
                updateWordActivity(wordId, folderId);
            }
        });
    }

    // Switch Activity -> updateWordActivity
    public void updateWordActivity(long wordId, long folderId) {
        Intent intent = new Intent(context, UpdateWordActivity.class);
        intent.putExtra("WORD_ID", wordId);
        intent.putExtra("FOLDER_ID", folderId);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    public void setWords(List<Word> words) {
        this.words = words;
        notifyDataSetChanged();
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final ConstraintLayout parent;
        private final TextView txtName, txtTranslation;
        ProgressBar statusBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            txtName = itemView.findViewById(R.id.txtName);
            txtTranslation = itemView.findViewById(R.id.txtTranslation);
            statusBar = itemView.findViewById(R.id.statusBar);
        }
    }
}
