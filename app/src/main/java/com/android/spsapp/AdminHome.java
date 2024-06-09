package com.android.spsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AdminHome extends AppCompatActivity {
    private ImageView mahasiswa, kaprodi, akun, logadmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_home);

        mahasiswa = findViewById(R.id.Maha);
        kaprodi = findViewById(R.id.kaprod);
        akun = findViewById(R.id.akunpengguna);
        logadmin = findViewById(R.id.logadmiin);

        mahasiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, MasaStudiMahasiswaAdmin.class);
                startActivity(intent);
            }
        });
        akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, AkunPengguna.class);
                startActivity(intent);
            }
        });
        kaprodi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, DataKaprodi.class);
                startActivity(intent);
            }
        });
        // Periksa apakah pengguna berhasil login
        boolean loggedIn = getIntent().getBooleanExtra("loggedIn", false);
        if (loggedIn) {
            // Jika pengguna berhasil login, tampilkan pesan "Selamat Datang"
            Toast.makeText(this, "Selamat Datang di AdminHome!", Toast.LENGTH_SHORT).show();

            // Ambil nama admin dari intent
            String namaAdmin = getIntent().getStringExtra("namaAdmin");

            // Temukan TextView untuk nama admin
            TextView textViewNamaAdmin = findViewById(R.id.namaadmin);

            // Set nama admin ke TextView
            textViewNamaAdmin.setText(namaAdmin);
        }

        logadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
    }
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(AdminHome.this, view);
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
        Intent intent = new Intent(AdminHome.this, Login.class);
        startActivity(intent);
        finish();
    }
}

