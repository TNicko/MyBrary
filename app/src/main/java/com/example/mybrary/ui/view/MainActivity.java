package com.example.mybrary.ui.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.mybrary.R;
import com.example.mybrary.network.ConnectionLiveData;
import com.example.mybrary.network.ConnectionModel;
import com.example.mybrary.ui.adapter.FolderRecViewAdapter;
import com.example.mybrary.ui.viewmodel.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private RecyclerView folderRecView;
    private FolderRecViewAdapter folderAdapter;
    private final MainViewModel mainViewModel = new MainViewModel();

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

        // Display Recycle View of all folders
        folderRecView = findViewById(R.id.folderRecView);
        folderAdapter = new FolderRecViewAdapter(this);
        folderRecView.setAdapter(folderAdapter);
        folderRecView.setLayoutManager(new LinearLayoutManager(this));


    }
}

