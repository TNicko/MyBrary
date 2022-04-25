package com.example.mybrary.ui.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.text.CaseMap;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import com.example.mybrary.R;
import com.example.mybrary.domain.model.Folder;
import com.example.mybrary.domain.model.Review;
import com.example.mybrary.domain.model.Word;
import com.example.mybrary.domain.util.BroadcastService;
import com.example.mybrary.domain.util.UploadWorker;
import com.example.mybrary.network.ConnectionLiveData;
import com.example.mybrary.network.ConnectionModel;
import com.example.mybrary.ui.adapter.FolderRecViewAdapter;
import com.example.mybrary.ui.viewmodel.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.concurrent.TimeUnit;

import kotlin.Triple;

public class MainActivity extends AppCompatActivity {

    private RecyclerView folderRecView;
    private FloatingActionButton addFolderBtn;
    private FolderRecViewAdapter folderAdapter;
    private MainViewModel mainViewModel;

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

        // Display Recycle View of all folders
        folderRecView = findViewById(R.id.folderRecView);
        folderAdapter = new FolderRecViewAdapter(this);
        mainViewModel.allFolderInfo.observe(this, new Observer<Triple<List<Folder>, List<Word>, List<Review>>>() {
            @Override
            public void onChanged(Triple<List<Folder>, List<Word>, List<Review>> listTriple) {
                folderAdapter.setData(listTriple.getFirst(), listTriple.getSecond(), listTriple.getThird());
                System.out.println(listTriple);
            }
        });


        folderRecView.setAdapter(folderAdapter);
        folderRecView.setLayoutManager(new LinearLayoutManager(this));

        // Add Folder Button
        addFolderBtn = findViewById(R.id.addFolderBtn);
        addFolderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newFolderActivity();
            }
        });

    }

    // Switch Activity -> FolderActivity
    public void folderActivity(long folderId) {
        Intent intent = new Intent(MainActivity.this, FolderActivity.class);
        intent.putExtra("FOLDER_ID", folderId);
        this.startActivity(intent);
    }

    // Switch Activity -> NewFolderActivity
    public void newFolderActivity() {
        Intent intent = new Intent(MainActivity.this, NewFolderActivity.class);
        this.startActivity(intent);
    }

}

