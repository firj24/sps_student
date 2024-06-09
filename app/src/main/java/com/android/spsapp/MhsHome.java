package com.android.spsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.spsapp.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MhsHome extends AppCompatActivity {

    private TextView namamhs;
    private String loggedInUsername;
    private ImageView masastudi, jurnal, logout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mhs_home);
        jurnal = findViewById(R.id.jurnalmhs);
        namamhs = findViewById(R.id.namamhs);
        masastudi = findViewById(R.id.mastudmhs);
        logout= findViewById(R.id.logmhs);
        masastudi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MhsHome.this, MasaStudiMahasiswa.class);
                intent.putExtra("USERNAME_KEY", loggedInUsername); // loggedInUsername adalah nama pengguna yang berhasil login
                startActivity(intent);
            }
        });
        jurnal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MhsHome.this, UpJurnalMhs.class);
                intent.putExtra("USERNAME_KEY", loggedInUsername);
                startActivity(intent);
            }
        });

        // Mendapatkan username yang berhasil login
        loggedInUsername = getIntent().getStringExtra("USERNAME_KEY");

        // Jika username ada, maka ambil nama akun dari Firestore
        if (loggedInUsername != null && !loggedInUsername.isEmpty()) {
            fetchAndSetNamaAkun();
        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
    }


    private void fetchAndSetNamaAkun() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("AkunMhs").document(loggedInUsername)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Dokumen ditemukan, ambil nilai nama akun
                        String namaAkun = documentSnapshot.getString("nama");
                        // Tetapkan nilai nama akun ke TextView
                        namamhs.setText(namaAkun);
                    } else {
                        // Dokumen tidak ditemukan
                        namamhs.setText("Nama Tidak Ditemukan");
                    }
                })
                .addOnFailureListener(e -> {
                    // Penanganan kesalahan
                    namamhs.setText("Gagal memuat nama");
                });
    }
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(MhsHome.this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_logout, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_logout) {
                    logout();
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void logout() {
        // Handle logout logic here
        Intent intent = new Intent(MhsHome.this, Login.class);
        startActivity(intent);
        finish();
    }
}
