package com.example.mybrary.ui.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mybrary.R;
import com.example.mybrary.ui.adapter.FolderRecViewAdapter;
import com.example.mybrary.ui.adapter.WordRecViewAdapter;
import com.example.mybrary.ui.viewmodel.FolderViewModel;
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

        // Get data from MainActivity
        Intent intent = getIntent();
        String folderName = intent.getStringExtra("FOLDER_NAME");

        addWordBtn = findViewById(R.id.addWordBtn);
        addWordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newWordActivity(folderName);
            }
        });
    }

    // Switch Activity -> newWordActivity
    public void newWordActivity(String folderName) {
        Intent intent = new Intent(FolderActivity.this, NewWordActivity.class);
        intent.putExtra("FOLDER_NAME", folderName);
        this.startActivity(intent);
    }
}