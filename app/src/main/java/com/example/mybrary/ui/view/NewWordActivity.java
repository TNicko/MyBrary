package com.example.mybrary.ui.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mybrary.R;
import com.example.mybrary.domain.util.UploadWorker;
import com.example.mybrary.network.ConnectionLiveData;
import com.example.mybrary.network.ConnectionModel;
import com.example.mybrary.ui.viewmodel.NewWordViewModel;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.concurrent.TimeUnit;

public class NewWordActivity extends AppCompatActivity {

    private NewWordViewModel newWordViewModel;
    private Button saveBtn, cancelBtn;
    private String folderId;
    Boolean isReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);

        // Tracks and displays changes to network connection
        ConnectionLiveData connectionLiveData = new ConnectionLiveData(getApplicationContext());
        connectionLiveData.observe(this, new Observer<ConnectionModel>() {
            @Override
            public void onChanged(ConnectionModel connection) {
                if (connection.getIsConnected()) {
                    switch (connection.getType()) {
                        case "Wifi":
                            Toast.makeText(NewWordActivity.this, "Connected to WIFI", Toast.LENGTH_SHORT).show();
                            break;
                        case "Mobile":
                            Toast.makeText(NewWordActivity.this, "Connected to mobile data", Toast.LENGTH_SHORT).show();
                            break;
                    }
                } else {
                    Toast.makeText(NewWordActivity.this, "No connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Get NewWordViewModel
        newWordViewModel = new ViewModelProvider(this).get(NewWordViewModel.class);

        // Get data from FolderActivity
        Intent intent = getIntent();
        folderId = intent.getStringExtra("FOLDER_ID");
        System.out.println("FOLDER ID = "+folderId);

        saveBtn = findViewById(R.id.saveBtn);
        cancelBtn = findViewById(R.id.cancelBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText wordInput, translationInput, notesInput;
                SwitchMaterial reviewInput = findViewById(R.id.wpSwitch);
                wordInput = findViewById(R.id.wpName);
                translationInput = findViewById(R.id.wpTranslation);
                notesInput = findViewById(R.id.wpNotes);

                String word, translation, notes;
                Boolean review;
                word = wordInput.getText().toString();
                translation = translationInput.getText().toString();
                notes = notesInput.getText().toString();
                review = reviewInput.isChecked();
                isReview = review;

                // Check/Add new Word
                String checkOutput = newWordViewModel.checkWordInput(word, translation, notes, review, folderId);
                if (checkOutput.equals("saved")){
                    newWordActivity();
                } else {
                    Toast.makeText(NewWordActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newWordActivity();
            }
        });

    }

    // Switch Activity -> FolderActivity
    public void newWordActivity() {
        Intent intent = new Intent(NewWordActivity.this, FolderActivity.class);
        intent.putExtra("FOLDER_ID", folderId);
        this.startActivity(intent);
    }
}