package com.example.mybrary.ui.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybrary.R;
import com.example.mybrary.domain.model.Folder;
import com.example.mybrary.domain.model.Review;
import com.example.mybrary.domain.model.Word;
import com.example.mybrary.network.ConnectionLiveData;
import com.example.mybrary.network.ConnectionModel;
import com.example.mybrary.ui.adapter.FolderRecViewAdapter;
import com.example.mybrary.ui.viewmodel.MainViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import kotlin.Triple;

public class MainActivity extends AppCompatActivity {

    private RecyclerView folderRecView;
    private FloatingActionButton addFolderBtn;
    private FolderRecViewAdapter folderAdapter;
    private MainViewModel mainViewModel;
    private ConstraintLayout reviewCard;
    private MaterialCardView reviewCardSection;
    private TextView reviewNum;
    private ImageView settingsBtn;
    private List<Review> readyReviews;
    List<Word> reviewReadyWords = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tracks and displays changes to network connection
        ConnectionLiveData connectionLiveData = new ConnectionLiveData(getApplicationContext());
        connectionLiveData.observe(this, new Observer<ConnectionModel>() {
            @Override
            public void onChanged(ConnectionModel connection) {
                if (connection.getIsConnected()) {
                    switch (connection.getType()) {
                        case "Wifi":
                            Toast.makeText(MainActivity.this, "Connected to WIFI", Toast.LENGTH_SHORT).show();
                            break;
                        case "Mobile":
                            Toast.makeText(MainActivity.this, "Connected to mobile data", Toast.LENGTH_SHORT).show();
                            break;
                    }
                } else {
                    Toast.makeText(MainActivity.this, "No connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Get MainViewModel
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        reviewNum = findViewById(R.id.reviewNum);
        addFolderBtn = findViewById(R.id.addFolderBtn);
        reviewCard = findViewById(R.id.reviewConstraint);
        reviewCardSection = findViewById(R.id.includeCard);
        folderRecView = findViewById(R.id.folderRecView);
        settingsBtn = findViewById(R.id.settingsBtn);

        // Display Recycle View of all folders
        folderAdapter = new FolderRecViewAdapter(this);
        mainViewModel.allFolderInfo.observe(this, new Observer<Triple<List<Folder>, List<Word>, List<Review>>>() {
            @Override
            public void onChanged(Triple<List<Folder>, List<Word>, List<Review>> listTriple) {
                folderAdapter.setData(listTriple.getFirst(), listTriple.getSecond(), listTriple.getThird());
                // All ready review objects
                readyReviews = listTriple.getThird()
                        .stream()
                        .filter(o -> !o.getTimer())
                        .collect(Collectors.toList());
                String reviewNumString = String.valueOf(readyReviews.size());
                reviewNum.setText(reviewNumString);

                // All ready review words
                for (Review review : readyReviews){
                    Word word = listTriple.getSecond().stream()
                            .filter(o -> o.getId().equals(review.getWordId()))
                            .findAny().orElse(null);
                    if (word != null){
                        reviewReadyWords.add(word);
                    }
                }

                if (readyReviews.size() > 0) {
                    reviewCardSection.setVisibility(View.VISIBLE);
                } else {
                    reviewCardSection.setVisibility(View.GONE);
                }
            }
        });


        folderRecView.setAdapter(folderAdapter);
        folderRecView.setLayoutManager(new LinearLayoutManager(this));

        // Add Folder Button

        addFolderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newFolderActivity();
            }
        });

        // Click on review card
        reviewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reviewActivity();
            }
        });

        // Settings button
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsActivity();
            }
        });

    }

    // Switch Activity -> FolderActivity
    public void folderActivity(String folderId) {
        Intent intent = new Intent(MainActivity.this, FolderActivity.class);
        intent.putExtra("FOLDER_ID", folderId);
        this.startActivity(intent);
    }

    // Switch Activity -> NewFolderActivity
    public void newFolderActivity() {
        Intent intent = new Intent(MainActivity.this, NewFolderActivity.class);
        this.startActivity(intent);
    }

    // Switch Activity -> ReviewActivity
    public void reviewActivity() {
        Intent intent = new Intent(MainActivity.this, ReviewActivity.class);
        Bundle bundleReviews = new Bundle();
        Bundle bundleWords = new Bundle();
        bundleReviews.putSerializable("REVIEW_LIST", (Serializable)readyReviews);
        bundleWords.putSerializable("WORD_LIST", (Serializable) reviewReadyWords);
        intent.putExtra("BUNDLE_REVIEW", bundleReviews);
        intent.putExtra("BUNDLE_WORD", bundleWords);
        this.startActivity(intent);
    }

    // Switch Activity -> SettingsActivity
    public void settingsActivity() {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        this.startActivity(intent);
    }

}

