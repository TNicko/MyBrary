package com.example.mybrary.ui.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mybrary.R;
import com.example.mybrary.domain.model.Word;
import com.example.mybrary.network.ConnectionLiveData;
import com.example.mybrary.network.ConnectionModel;
import com.example.mybrary.ui.factory.WordViewModelFactory;
import com.example.mybrary.ui.viewmodel.UpdateWordViewModel;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.List;

public class UpdateWordActivity extends AppCompatActivity {

    private UpdateWordViewModel updateWordViewModel;
    private Long folderId;
    private EditText wordInput, translationInput, notesInput;
    private Button saveBtn, cancelBtn, deleteBtn;
    private SwitchMaterial switchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_word);

        // Tracks and displays changes to network connection
        ConnectionLiveData connectionLiveData = new ConnectionLiveData(getApplicationContext());
        connectionLiveData.observe(this, new Observer<ConnectionModel>() {
            @Override
            public void onChanged(ConnectionModel connection) {
                if (connection.getIsConnected()) {
                    switch (connection.getType()) {
                        case "Wifi":
                            Toast.makeText(UpdateWordActivity.this, "Connected to WIFI", Toast.LENGTH_SHORT).show();
                            break;
                        case "Mobile":
                            Toast.makeText(UpdateWordActivity.this, "Connected to mobile data", Toast.LENGTH_SHORT).show();
                            break;
                    }
                } else {
                    Toast.makeText(UpdateWordActivity.this, "No connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Get Data from Previous Activity
        Intent intent = getIntent();
        long wordId = intent.getLongExtra("WORD_ID", 0);
        System.out.println("WORD ID = "+wordId);

        // Get UpdateWordViewModel
        updateWordViewModel = new ViewModelProvider(this, new WordViewModelFactory(this.getApplication(), wordId)).get(UpdateWordViewModel.class);


        // Add info to input fields
        wordInput = findViewById(R.id.wpName);
        translationInput = findViewById(R.id.wpTranslation);
        notesInput = findViewById(R.id.wpNotes);
        switchInput = findViewById(R.id.wpSwitch);
        setWordInfo();

    }

    private void setWordInfo() {

        updateWordViewModel.returnWord().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                wordInput.setText(words.get(0).getWord());
                translationInput.setText(words.get(0).getTranslation());
                notesInput.setText(words.get(0).getNotes());
                switchInput.setChecked(words.get(0).isReview());
            }
        });
    }
}