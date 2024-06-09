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

import androidx.appcompat.app.AppCompatActivity;

public class HomeKaprodi extends AppCompatActivity {
    private ImageView btnJurn,logkap, btnMasaStudKap;
    private TextView namaKaprodi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_kaprodi);

        btnJurn = findViewById(R.id.jurnalmhskap);
        namaKaprodi = findViewById(R.id.namakaprodi);
        logkap = findViewById(R.id.logkap);
        btnMasaStudKap = findViewById(R.id.masastudkap);
        btnMasaStudKap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeKaprodi.this, MasaStudiMahasiswaAdmin.class);
                startActivity(intent);
            }
        });

        // Get the namaKaprodi from the intent
        String nama = getIntent().getStringExtra("NAMA_KAPRODI");
        if (nama != null) {
            namaKaprodi.setText(nama);
        }

        btnJurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeKaprodi.this, JurnalMhsKap.class);
                startActivity(intent);
            }
        });

        // Set onClickListener for the logkap ImageView
        logkap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(HomeKaprodi.this, view);
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
        Intent intent = new Intent(HomeKaprodi.this, Login.class);
        startActivity(intent);
        finish();
    }
}
