package com.example.mybrary.ui.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybrary.R;
import com.example.mybrary.ui.viewmodel.LoginViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private LoginViewModel loginViewModel;

    private Button loginBtn;
    private TextView registerBtn;
    private TextInputLayout emailLayout, passwordLayout;
    private TextInputEditText emailInput, passwordInput;
    private TextView registerTxt;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private static final String TAG = "Authentication: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.w(TAG, "User is signed in: "+user.getEmail());
            mainActivity();
        } else {
            Log.w(TAG, "No User signed in.");
        }

        mAuth = FirebaseAuth.getInstance();

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        loginBtn = findViewById(R.id.loginBtn);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        registerTxt = findViewById(R.id.registerTxt);
        registerBtn = findViewById(R.id.registerBtn);
        passwordLayout = findViewById(R.id.passwordField);
        emailLayout = findViewById(R.id.textField);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerActivity();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString();

                // Validation
                if (TextUtils.isEmpty(email)) {
                    emailLayout.setError("Email is required");
                }
                else if (!email.matches(emailPattern)) {
                    emailLayout.setError("Email is invalid");
                }
                else if (TextUtils.isEmpty(password)) {
                    passwordLayout.setError("Password is required");
                }
                else {
                    loginUser(email, password);
                }

            }
        });
    }

    // Switch Activity ->RegisterActivity
    public void registerActivity() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        this.startActivity(intent);
    }

    public void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void updateUI() {
        loginViewModel.loadAll();
        mainActivity();
    }

    // Switch Activity -> MainActivity
    public void mainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        this.startActivity(intent);
    }
}