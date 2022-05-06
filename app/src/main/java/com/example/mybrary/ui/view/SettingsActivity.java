package com.example.mybrary.ui.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mybrary.R;
import com.example.mybrary.data.repository.FolderRepository;
import com.example.mybrary.ui.viewmodel.MainViewModel;
import com.example.mybrary.ui.viewmodel.SettingsViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {

    TextView logo;
    Button logoutBtn;
    SettingsViewModel settingsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Get SettingsViewModel
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        logo = findViewById(R.id.logo);
        logoutBtn = findViewById(R.id.logoutBtn);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                settingsViewModel.nukeDatabase();
                loginActivity();
            }
        });

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity();
            }
        });
    }

    // Switch Activity -> MainActivity
    public void mainActivity() {
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        this.startActivity(intent);
    }

    // Switch Activity -> LoginActivity
    public void loginActivity() {
        Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
        this.startActivity(intent);
    }
}