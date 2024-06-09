package com.android.spsapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.android.spsapp.Model.AkunMahasiswaa;
import java.util.UUID;

public class TambahAKunMhs extends AppCompatActivity {

    private EditText namaEditText, usernameEditText, passwordEditText, noHpEditText;
    private Button tambahAkunButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_akun_mhs);

        namaEditText = findViewById(R.id.inpnamamhs);
        usernameEditText = findViewById(R.id.inpusernamemhs);
        passwordEditText = findViewById(R.id.inppassmhs);
        noHpEditText = findViewById(R.id.inpnoHpMhs);
        tambahAkunButton = findViewById(R.id.tambahakunmhs);
        db = FirebaseFirestore.getInstance();

        tambahAkunButton.setOnClickListener(v -> tambahAkunMahasiswa());
    }

    private void tambahAkunMahasiswa() {
        String nama = namaEditText.getText().toString();
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String noHp = noHpEditText.getText().toString();
        String nim = ""; // Tidak diinputkan
        String angkatan = ""; // Tidak diinputkan
        String prodi = ""; // Tidak diinputkan
        String sks = ""; // Tidak diinputkan
        String ipk = ""; // Tidak diinputkan
        String yudisium = ""; // Tidak diinputkan

        if (TextUtils.isEmpty(username)) {
            usernameEditText.setError("Masukkan username");
            return;
        }

        db.collection("AkunMhs").document(username).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Username sudah digunakan
                            Toast.makeText(TambahAKunMhs.this, "Username sudah digunakan", Toast.LENGTH_SHORT).show();
                        } else {
                            // Username belum digunakan, simpan data
                            AkunMahasiswaa akunMahasiswa = new AkunMahasiswaa();
                            akunMahasiswa.setUserId(username); // Gunakan username sebagai ID dokumen
                            akunMahasiswa.setNama(nama);
                            akunMahasiswa.setUsername(username);
                            akunMahasiswa.setPassword(password);
                            akunMahasiswa.setNoHp(noHp);

                            db.collection("AkunMhs").document(username).set(akunMahasiswa)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(TambahAKunMhs.this, "Akun berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(TambahAKunMhs.this, AkunMahasiswa.class);
                                        startActivity(intent);
                                        finish();
                                    })
                                    .addOnFailureListener(e -> Toast.makeText(TambahAKunMhs.this, "Gagal menambahkan akun", Toast.LENGTH_SHORT).show());
                        }
                    } else {
                        Toast.makeText(TambahAKunMhs.this, "Gagal memeriksa username: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
