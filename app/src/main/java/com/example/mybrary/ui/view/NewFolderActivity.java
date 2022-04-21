package com.example.mybrary.ui.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mybrary.R;
import com.example.mybrary.ui.viewmodel.MainViewModel;
import com.example.mybrary.ui.viewmodel.NewFolderViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class NewFolderActivity extends AppCompatActivity {

    private NewFolderViewModel newFolderViewModel;
    private Button saveBtn, cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_folder);

        newFolderViewModel = new ViewModelProvider(this).get(NewFolderViewModel.class);


        saveBtn = findViewById(R.id.saveBtn);
        cancelBtn = findViewById(R.id.cancelBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Save Button Clicked");
                TextInputEditText folderInputField =  findViewById(R.id.folderInput);
                String folderInput = folderInputField.getText().toString();
                // !!! pass to function in viewmodel
                String checkOutput = newFolderViewModel.checkFolderInput(folderInput);
                if (checkOutput.equals("saved")){
                    newFolderActivity();
                } else {
                    Toast.makeText(NewFolderActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newFolderActivity();
            }
        });


    }

    // Switch Activity -> MainActivity
    public void newFolderActivity() {
        Intent intent = new Intent(NewFolderActivity.this, MainActivity.class);
        this.startActivity(intent);
    }
}