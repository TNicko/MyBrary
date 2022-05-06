package com.example.mybrary.ui.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybrary.R;
import com.example.mybrary.domain.model.Review;
import com.example.mybrary.domain.model.Word;
import com.example.mybrary.network.ConnectionLiveData;
import com.example.mybrary.network.ConnectionModel;
import com.example.mybrary.ui.adapter.WordRecViewAdapter;
import com.example.mybrary.ui.viewmodel.FolderViewModel;
import com.example.mybrary.ui.factory.FolderViewModelFactory;

import java.util.List;

public class FolderActivity extends AppCompatActivity {

    private RecyclerView wordRecView;
    private Button addWordBtn;
    private WordRecViewAdapter wordAdapter;
    private FolderViewModel folderViewModel;
    private TextView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder);

        // Tracks and displays changes to network connection
        ConnectionLiveData connectionLiveData = new ConnectionLiveData(getApplicationContext());
        connectionLiveData.observe(this, new Observer<ConnectionModel>() {
            @Override
            public void onChanged(ConnectionModel connection) {
                if (connection.getIsConnected()) {
                    switch (connection.getType()) {
                        case "Wifi":
                            Toast.makeText(FolderActivity.this, "Connected to WIFI", Toast.LENGTH_SHORT).show();
                            break;
                        case "Mobile":
                            Toast.makeText(FolderActivity.this, "Connected to mobile data", Toast.LENGTH_SHORT).show();
                            break;
                    }
                } else {
                    Toast.makeText(FolderActivity.this, "No connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Get data from Previous Activity
        Intent intent = getIntent();
        String folderId = intent.getStringExtra("FOLDER_ID");
        System.out.println("FOLDER ID = "+folderId);

        // Get FolderViewModel
//        folderViewModel = new ViewModelProvider(this).get(FolderViewModel.class);
        folderViewModel = new ViewModelProvider(this, new FolderViewModelFactory(this.getApplication(), folderId)).get(FolderViewModel.class);

        // Display Recycle View of all words
        wordRecView = findViewById(R.id.wordRecView);
        wordAdapter = new WordRecViewAdapter(this);
        folderViewModel.allWordInfo.observe(this, new Observer<Pair<List<Word>, List<Review>>>() {
            @Override
            public void onChanged(Pair<List<Word>, List<Review>> listListPair) {
                System.out.println("word Info rec view: "+listListPair);
                wordAdapter.setData(listListPair.first, listListPair.second);

            }
        });

        wordRecView.setAdapter(wordAdapter);
        wordRecView.setLayoutManager(new LinearLayoutManager(this));

        // Add Word Button clicked -> NewWordActivity
        addWordBtn = findViewById(R.id.addWordBtn);
        addWordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newWordActivity(folderId);
            }
        });

        // Logo Clicked -> MainActivity
        logo = findViewById(R.id.logo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity();
            }
        });
    }

    // Switch Activity -> newWordActivity
    public void newWordActivity(String folderId) {
        Intent intent = new Intent(FolderActivity.this, NewWordActivity.class);
        intent.putExtra("FOLDER_ID", folderId);
        this.startActivity(intent);
    }

    // Switch Activity -> MainActivity
    public void mainActivity() {
        Intent intent = new Intent(FolderActivity.this, MainActivity.class);
        this.startActivity(intent);
    }


}

