package com.android.spsapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.android.spsapp.Model.Kaprodi;
import com.google.firebase.firestore.FirebaseFirestore;

public class TambahDataKaprodi extends AppCompatActivity {

    private EditText inpnpm, inpnamakap, inpprodikap, inpemailkap, inppasskap;
    private Button simkapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data_kaprodi);

        inpnpm = findViewById(R.id.inpnpm);
        inpnamakap = findViewById(R.id.inpnamakap);
        inpprodikap = findViewById(R.id.inpprodikap);
        inpemailkap = findViewById(R.id.inpemailkap);
        inppasskap = findViewById(R.id.inppasskap);
        simkapp = findViewById(R.id.simkapp);

        simkapp.setOnClickListener(v -> addKaprodi());
    }

    private void addKaprodi() {
        String npm = inpnpm.getText().toString();
        String nama = inpnamakap.getText().toString();
        String prodi = inpprodikap.getText().toString();
        String email = inpemailkap.getText().toString();
        String password = inppasskap.getText().toString();

        Kaprodi kaprodi = new Kaprodi(npm, npm, nama, prodi, email, password);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("DataKaprodi").document(npm).set(kaprodi)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(TambahDataKaprodi.this, "Data Kaprodi berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TambahDataKaprodi.this, DataKaprodi.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(TambahDataKaprodi.this, "Gagal menambahkan data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
