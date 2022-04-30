package com.example.mybrary.ui.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mybrary.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    TextInputEditText emailInput;
    TextView registerTxt;
    private FirebaseAuth fAuth;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private static final String TAG = "Firebase Login";
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.loginBtn);
        emailInput = findViewById(R.id.emailInput);
        registerTxt = findViewById(R.id.registerTxt);

        fAuth = FirebaseAuth.getInstance();

        ActionCodeSettings actionCodeSettings;

        actionCodeSettings = ActionCodeSettings.newBuilder()
                .setUrl("https://mybrary.com/learn")
                .setHandleCodeInApp(true)
                .setAndroidPackageName("com.example.mybrary", false, null)
                .build();


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailInput.getText().toString().trim();

                // Validation
                if (TextUtils.isEmpty(email)) {
                    emailInput.setError("Email is required");
                }
                else if (!email.matches(emailPattern)) {
                    emailInput.setError("Email is invalid");
                }
                else {
                    fAuth.sendSignInLinkToEmail(email, actionCodeSettings)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.d(TAG, "onComplete: ");
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "Email sent.");
                                    } else {
                                        Objects.requireNonNull(task.getException()).printStackTrace();
                                    }
                                }
                            });
                }

            }
        });
    }
}