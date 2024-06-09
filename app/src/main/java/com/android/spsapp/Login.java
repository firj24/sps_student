package com.android.spsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    private EditText inputUsername, inputPassword;
    private Button btnLogin;
    private FirebaseFirestore db;
    private ProgressDialog progressDialog;
    private ImageView btnHide;
    private String loggedInUsername;

    private boolean isPasswordVisible = false;

    private String savedUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputUsername = findViewById(R.id.username);
        inputPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.login);
        db = FirebaseFirestore.getInstance();
        btnHide = findViewById(R.id.hide);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in...");
        progressDialog.setCancelable(false);

        if (savedInstanceState != null) {
            savedUsername = savedInstanceState.getString("username");
            inputUsername.setText(savedUsername);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        btnHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("username", inputUsername.getText().toString());
    }

    private void login() {
        final String username = inputUsername.getText().toString().trim();
        final String password = inputPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            inputUsername.setError("Enter username");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            inputPassword.setError("Enter password");
            return;
        }

        progressDialog.show();

        // Check login for admin
        db.collection("admins").document(username).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String storedPassword = document.getString("password");
                                if (password.equals(storedPassword)) {
                                    savedUsername = username;
                                    Intent intent = new Intent(Login.this, AdminHome.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(Login.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // If no admin account, check for student account
                                loginMhs();
                            }
                        } else {
                            Toast.makeText(Login.this, "Failed to fetch data: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void loginMhs() {
        final String username = inputUsername.getText().toString().trim();
        final String password = inputPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            inputUsername.setError("Enter username");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            inputPassword.setError("Enter password");
            return;
        }

        progressDialog.show();

        // Check login for student
        db.collection("AkunMhs").document(username).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        loggedInUsername = username;

                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String storedPassword = document.getString("password");
                                if (password.equals(storedPassword)) {
                                    loggedInUsername = username;
                                    savedUsername = username;
                                    Intent intent = new Intent(Login.this, MhsHome.class);
                                    intent.putExtra("USERNAME_KEY", loggedInUsername);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(Login.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // If no student account, check for Kaprodi account
                                loginKaprodi();
                            }
                        } else {
                            Toast.makeText(Login.this, "Failed to fetch data: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void loginKaprodi() {
        final String npm = inputUsername.getText().toString().trim();
        final String password = inputPassword.getText().toString().trim();

        if (TextUtils.isEmpty(npm)) {
            inputUsername.setError("Enter NPM");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            inputPassword.setError("Enter password");
            return;
        }

        progressDialog.show();

        // Use npm as UID to check Kaprodi account in Firestore
        db.collection("DataKaprodi").document(npm).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String storedPassword = document.getString("password");
                                if (password.equals(storedPassword)) {
                                    loggedInUsername = npm;
                                    savedUsername = npm;
                                    String namaKaprodi = document.getString("nama");

                                    Intent intent = new Intent(Login.this, HomeKaprodi.class);
                                    intent.putExtra("NAMA_KAPRODI", namaKaprodi);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(Login.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Login.this, "NPM not registered", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Login.this, "Failed to fetch data: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void togglePasswordVisibility() {
        if (!isPasswordVisible) {
            inputPassword.setTransformationMethod(null);
            btnHide.setImageResource(R.drawable.baseline_visibility_off_24);
        } else {
            inputPassword.setTransformationMethod(new PasswordTransformationMethod());
            btnHide.setImageResource(R.drawable.baseline_visibility_24);
        }
        isPasswordVisible = !isPasswordVisible;
        inputPassword.setSelection(inputPassword.getText().length());
    }
}
