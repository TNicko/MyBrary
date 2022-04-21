package com.example.mybrary.ui.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mybrary.R;
import com.example.mybrary.network.ConnectionLiveData;
import com.example.mybrary.network.ConnectionModel;
import com.example.mybrary.ui.adapter.FolderRecViewAdapter;
import com.example.mybrary.ui.adapter.WordRecViewAdapter;
import com.example.mybrary.ui.viewmodel.FolderViewModel;
import com.example.mybrary.ui.viewmodel.FolderViewModelFactory;
import com.example.mybrary.ui.viewmodel.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FolderActivity extends AppCompatActivity {

    private RecyclerView wordRecView;
    private Button addWordBtn;
    private WordRecViewAdapter wordAdapter;
    private FolderViewModel folderViewModel;

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
        long folderId = intent.getLongExtra("FOLDER_ID", 0);
        System.out.println("FOLDER ID = "+folderId);

        // Get FolderViewModel
//        folderViewModel = new ViewModelProvider(this).get(FolderViewModel.class);
        folderViewModel = new ViewModelProvider(this, new FolderViewModelFactory(this.getApplication(), folderId)).get(FolderViewModel.class);

        // Display Recycle View of all words
        wordRecView = findViewById(R.id.wordRecView);
        wordAdapter = new WordRecViewAdapter(this);
        folderViewModel.words.observe(this, words -> {
            System.out.println(words);
            wordAdapter.setWords(words);
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
    }

    // Switch Activity -> newWordActivity
    public void newWordActivity(long folderId) {
        Intent intent = new Intent(FolderActivity.this, NewWordActivity.class);
        intent.putExtra("FOLDER_ID", folderId);
        this.startActivity(intent);
    }
}

