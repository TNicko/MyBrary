package com.example.mybrary.ui.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mybrary.R;
import com.example.mybrary.ui.viewmodel.NewFolderViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class NewFolderActivity extends AppCompatActivity {

    NewFolderViewModel newFolderViewModel = new NewFolderViewModel();
    Button saveBtn, cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_folder);

        saveBtn = findViewById(R.id.saveBtn);
        cancelBtn = findViewById(R.id.cancelBtn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText folderInputField =  findViewById(R.id.folderInput);
                String folderInput = folderInputField.getText().toString();
                // !!! pass to function in viewmodel
                String checkOutput = newFolderViewModel.checkFolderInput(folderInput);
            }
        });
    }

    // Switch Activity -> MainActivity
    public void newFolderActivity() {
        Intent intent = new Intent(NewFolderActivity.this, MainActivity.class);
        this.startActivity(intent);
    }
}