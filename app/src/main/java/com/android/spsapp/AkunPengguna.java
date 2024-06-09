package com.android.spsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AkunPengguna extends AppCompatActivity {

    private ImageView akunmhs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_akun_pengguna);

        akunmhs = findViewById(R.id.akunmahasiswa);

        akunmhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AkunPengguna.this, AkunMahasiswa.class);
                startActivity(intent);
            }
        });
    }
}