// EditMastudMhs.java
package com.android.spsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class EditMastudMhs extends AppCompatActivity {

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mastud_mhs);

        db = FirebaseFirestore.getInstance();

        Button simpanButton = findViewById(R.id.simpann);
        simpanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ambil nilai dari semua inputan
                String nama = ((EditText) findViewById(R.id.namamhsss)).getText().toString();
                String nim = ((EditText) findViewById(R.id.nimmhss)).getText().toString();
                String prodi = ((EditText) findViewById(R.id.prodimhss)).getText().toString();
                String angkatan = ((EditText) findViewById(R.id.angkatanmhss)).getText().toString();
                String ipk = ((EditText) findViewById(R.id.ipkmhss)).getText().toString();
                String sks = ((EditText) findViewById(R.id.sksmhss)).getText().toString();
                String yudisium = ((EditText) findViewById(R.id.yudimhss)).getText().toString();

                // Simpan data ke Firestore
                simpanDataFirestore(nama, nim, prodi, angkatan, ipk, sks, yudisium);

                // Kirim nilai-nilai tersebut kembali ke activity sebelumnya
                Intent intent = new Intent();
                intent.putExtra("nama", nama);
                intent.putExtra("nim", nim);
                intent.putExtra("prodi", prodi);
                intent.putExtra("angkatan", angkatan);
                intent.putExtra("ipk", ipk);
                intent.putExtra("sks", sks);
                intent.putExtra("yudisium", yudisium);
                setResult(RESULT_OK, intent);
                finish(); // Selesai activity ini dan kembali ke activity sebelumnya
            }
        });
    }

    private void simpanDataFirestore(String nama, String nim, String prodi, String angkatan, String ipk, String sks, String yudisium) {
        // Akses koleksi "MasaStudiMHS" di Firestore
        DocumentReference docRef = db.collection("MasaStudiMHS").document();

        // Buat objek Map untuk menyimpan data
        Map<String, Object> data = new HashMap<>();
        data.put("nama", nama);
        data.put("nim", nim);
        data.put("prodi", prodi);
        data.put("angkatan", angkatan);
        data.put("ipk", ipk);
        data.put("sks", sks);
        data.put("yudisium", yudisium);

        // Simpan data ke Firestore dengan opsi merge untuk pembaruan data
        docRef.set(data, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    // Jika berhasil disimpan, tampilkan pesan sukses (opsional)
                    Toast.makeText(EditMastudMhs.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Jika terjadi kesalahan, tampilkan pesan error (opsional)
                    Toast.makeText(EditMastudMhs.this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
                });
    }
}
