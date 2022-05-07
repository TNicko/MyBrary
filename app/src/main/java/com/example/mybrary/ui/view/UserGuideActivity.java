package com.example.mybrary.ui.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mybrary.R;

public class UserGuideActivity extends AppCompatActivity {

    WebView webView;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide);

        webView = findViewById(R.id.webView);
        backBtn = findViewById(R.id.backBtn);

        webView.loadUrl("file:///android_asset/UserGuide.html");


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity();
            }
        });



    }

    // Switch Activity -> MainActivity
    public void mainActivity() {
        Intent intent = new Intent(UserGuideActivity.this, MainActivity.class);
        this.startActivity(intent);
    }
}