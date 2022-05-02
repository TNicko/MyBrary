package com.example.mybrary.ui.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.authentication.PasswordlessType;
import com.auth0.android.callback.Callback;
import com.auth0.android.lock.LockCallback;
import com.auth0.android.lock.PasswordlessLock;
import com.auth0.android.result.Credentials;
import com.example.mybrary.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    Auth0 account;
    AuthenticationAPIClient authentication;
    private FirebaseAuth firebaseAuth;
    FileInputStream serviceAccount;
    FirebaseOptions options;

    Button loginBtn, codeBtn;
    TextInputEditText emailInput, codeInput;
    TextInputLayout emailField, codeField;
    TextView registerTxt;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private static final String TAG = "Authentication: ";
    String email, code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        account = new Auth0(this);
        authentication = new AuthenticationAPIClient(account);
        firebaseAuth = FirebaseAuth.getInstance();



        loginBtn = findViewById(R.id.loginBtn);
        emailInput = findViewById(R.id.emailInput);
        emailField = findViewById(R.id.textField);
        registerTxt = findViewById(R.id.registerTxt);
        codeBtn = findViewById(R.id.codeBtn);
        codeInput = findViewById(R.id.codeInput);
        codeField = findViewById(R.id.codeField);

        codeInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                code = codeInput.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    codeBtn.setVisibility(View.GONE);
                } else {
                    codeBtn.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });


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
                    authentication.passwordlessWithEmail(email, PasswordlessType.CODE)
                            .start(new Callback<Void, AuthenticationException>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "Code sent.");
                                    registerTxt.setText(getResources().getString(R.string.enter_code));
                                    emailField.setVisibility(View.GONE);
                                    loginBtn.setVisibility(View.GONE);
                                    codeField.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onFailure(@NonNull AuthenticationException e) {
                                    Log.d(TAG, "Failure: "+e);
                                }
                            });
                }

            }
        });

        codeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                code = codeInput.getText().toString().trim();

                // Validation
                if (TextUtils.isEmpty(code)) {
                    codeInput.setError("Email is required");
                }
                else {
                    authentication
                            .loginWithEmail(email, code)
                            .start(new Callback<Credentials, AuthenticationException>() {
                                @Override
                                public void onSuccess(Credentials credentials) {
                                    Log.d(TAG, "Successfully logged in");
                                    String idToken = credentials.getIdToken();
                                    Map<String, Object> additionalClaims = new HashMap<String, Object>();
                                    additionalClaims.put("premiumAccount", true);
                                    String customToken;
                                    try {
                                        System.out.println(customToken);

//                                        firebaseAuth.signInWithCustomToken(customToken)
//                                                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
//                                                    @Override
//                                                    public void onComplete(@NonNull Task<AuthResult> task) {
//                                                        if (task.isSuccessful()) {
//                                                            // Sign in success, update UI
//                                                            Log.d(TAG, "signInWithToken: success");
//                                                            FirebaseUser user = firebaseAuth.getCurrentUser();
//                                                        } else {
//                                                            // If sign in fails, display a message to the user.
//                                                            Log.w(TAG, "signInWithToken: failure", task.getException());
//                                                        }
//                                                    }
//                                                });
                                    } catch (ExecutionException | InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull AuthenticationException e) {
                                    Log.d(TAG, "Failure: "+e);

                                }
                            });
                }
            }
        });
    }

    // Switch Activity -> MainActivity
    public void mainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        this.startActivity(intent);
    }
}