package com.android.spsapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.spsapp.Model.MahasiswaAdminModel;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditMahasiswaActivity extends AppCompatActivity {

    private EditText editNama, editNim, editProdi, editAngkatan, editIpk, editSks, editYudisium;
    private Button btnSave;
    private FirebaseFirestore db;
    private String documentId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mahasiswa);

        editNama = findViewById(R.id.editNama);
        editNim = findViewById(R.id.editNim);
        editProdi = findViewById(R.id.editProdi);
        editAngkatan = findViewById(R.id.editAngkatan);
        editIpk = findViewById(R.id.editIpk);
        editSks = findViewById(R.id.editSks);
        editYudisium = findViewById(R.id.editYudisium);
        btnSave = findViewById(R.id.btnSave);
        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        if (intent != null) {
            editNama.setText(intent.getStringExtra("nama"));
            editNim.setText(intent.getStringExtra("nim"));
            editProdi.setText(intent.getStringExtra("prodi"));
            editAngkatan.setText(intent.getStringExtra("angkatan"));
            editIpk.setText(intent.getStringExtra("ipk"));
            editSks.setText(intent.getStringExtra("sks"));
            editYudisium.setText(intent.getStringExtra("yudisium"));
            documentId = intent.getStringExtra("documentId");
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMahasiswa();
            }
        });
    }

    private void updateMahasiswa() {
        String nama = editNama.getText().toString().trim();
        String nim = editNim.getText().toString().trim();
        String prodi = editProdi.getText().toString().trim();
        String angkatan = editAngkatan.getText().toString().trim();
        String ipk = editIpk.getText().toString().trim();
        String sks = editSks.getText().toString().trim();
        String yudisium = editYudisium.getText().toString().trim();

        if (TextUtils.isEmpty(nama) || TextUtils.isEmpty(nim) || TextUtils.isEmpty(prodi) ||
                TextUtils.isEmpty(angkatan) || TextUtils.isEmpty(ipk) || TextUtils.isEmpty(sks) ||
                TextUtils.isEmpty(yudisium)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        MahasiswaAdminModel updatedMahasiswa = new MahasiswaAdminModel(nim, nama, prodi, angkatan, ipk, sks, yudisium);

        db.collection("DataMhs").document(documentId)
                .update("nama", updatedMahasiswa.getNama(),
                        "nim", updatedMahasiswa.getNim(),
                        "prodi", updatedMahasiswa.getProdi(),
                        "angkatan", updatedMahasiswa.getAngkatan(),
                        "ipk", updatedMahasiswa.getIpk(),
                        "sks", updatedMahasiswa.getSks(),
                        "yudisium", updatedMahasiswa.getYudisium())
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(EditMahasiswaActivity.this, "Data updated", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(EditMahasiswaActivity.this, "Failed to update data", Toast.LENGTH_SHORT).show();
                });
    }

}
