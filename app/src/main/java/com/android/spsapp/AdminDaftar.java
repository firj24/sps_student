package com.android.spsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.spsapp.Model.Admin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminDaftar extends AppCompatActivity {

    private EditText inputNama, inputUsername, inputPassword;
    private Button btnDaftar;
    private ProgressDialog progressDialog;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_daftar);

        inputNama = findViewById(R.id.nama);
        inputUsername = findViewById(R.id.username);
        inputPassword = findViewById(R.id.password);
        btnDaftar = findViewById(R.id.daftaradmin);
        db = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mendaftarkan akun...");
        progressDialog.setCancelable(false); // Jangan izinkan pengguna membatalkan ProgressDialog dengan mengetuk di luar area dialog

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daftarAdmin();
            }
        });
    }

    private void daftarAdmin() {
        final String nama = inputNama.getText().toString().trim();
        final String username = inputUsername.getText().toString().trim();
        final String password = inputPassword.getText().toString().trim();

        if (TextUtils.isEmpty(nama)) {
            inputNama.setError("Masukkan nama");
            return;
        }

        if (TextUtils.isEmpty(username)) {
            inputUsername.setError("Masukkan username");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            inputPassword.setError("Masukkan password");
            return;
        }

        progressDialog.show(); // Tampilkan ProgressDialog saat proses dimulai

        // Simpan data ke Firestore
        db.collection("admins").document(username).set(new Admin(nama, username, password))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss(); // Sembunyikan ProgressDialog saat proses selesai

                        if (task.isSuccessful()) {
                            Toast.makeText(AdminDaftar.this, "Akun berhasil didaftarkan", Toast.LENGTH_SHORT).show();
                            // Redirect to login activity
                            startActivity(new Intent(AdminDaftar.this, Login.class));
                            finish();
                        } else {
                            Toast.makeText(AdminDaftar.this, "Gagal mendaftarkan akun: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

