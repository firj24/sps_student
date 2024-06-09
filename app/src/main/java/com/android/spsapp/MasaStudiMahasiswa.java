package com.android.spsapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

public class MasaStudiMahasiswa extends AppCompatActivity {

    private static final String TAG = "MasaStudiMahasiswa";

    private TextView Nama, Nim, Prodi, Angkatan, SKS, IPK, Yudisium;
    private String loggedInUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masa_studi_mahasiswa);

        // Inisialisasi TextView
        Nama = findViewById(R.id.namamhsmhs);
        Nim = findViewById(R.id.nimmhsmhs);
        Prodi = findViewById(R.id.prodimhsmhs);
        Angkatan = findViewById(R.id.angkatanmhsmhs);
        SKS = findViewById(R.id.sksmhsmhs);
        IPK = findViewById(R.id.ipkmhsmhs);
        Yudisium = findViewById(R.id.yudmhsmhs);

        // Mendapatkan username yang berhasil login
        loggedInUsername = getIntent().getStringExtra("USERNAME_KEY");

        // Log username untuk memastikan itu tidak null atau kosong
        Log.d(TAG, "Logged in username: " + loggedInUsername);

        // Jika username ada, maka ambil data mahasiswa dari Firestore
        if (loggedInUsername != null && !loggedInUsername.isEmpty()) {
            fetchAndSetDataMahasiswa();
        } else {
            Log.e(TAG, "Username is null or empty");
        }
    }

    private void fetchAndSetDataMahasiswa() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("AkunMhs").document(loggedInUsername)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Dokumen ditemukan, ambil data mahasiswa
                        String nama = documentSnapshot.getString("nama");
                        String nim = documentSnapshot.getString("nim");
                        String prodi = documentSnapshot.getString("prodi");
                        String angkatan = documentSnapshot.getString("angkatan");
                        String sks = documentSnapshot.getString("sks");
                        String ipk = documentSnapshot.getString("ipk");
                        String yudisium = documentSnapshot.getString("yudisium");

                        // Log data untuk debugging
                        Log.d(TAG, "Nama: " + nama);
                        Log.d(TAG, "NIM: " + nim);
                        Log.d(TAG, "Prodi: " + prodi);
                        Log.d(TAG, "Angkatan: " + angkatan);
                        Log.d(TAG, "SKS: " + sks);
                        Log.d(TAG, "IPK: " + ipk);
                        Log.d(TAG, "Yudisium: " + yudisium);

                        // Set nilai TextView dengan data mahasiswa
                        Nama.setText(nama);
                        Nim.setText(nim);
                        Prodi.setText(prodi);
                        Angkatan.setText(angkatan);
                        SKS.setText(sks);
                        IPK.setText(ipk);
                        Yudisium.setText(yudisium);
                    } else {
                        // Dokumen tidak ditemukan
                        Log.e(TAG, "Document does not exist");
                        Nama.setText("Nama Tidak Ditemukan");
                        Nim.setText("NIM Tidak Ditemukan");
                        Prodi.setText("Prodi Tidak Ditemukan");
                        Angkatan.setText("Angkatan Tidak Ditemukan");
                        SKS.setText("SKS Tidak Ditemukan");
                        IPK.setText("IPK Tidak Ditemukan");
                        Yudisium.setText("Yudisium Tidak Ditemukan");
                    }
                })
                .addOnFailureListener(e -> {
                    // Penanganan kesalahan saat mengambil data dari Firestore
                    Log.e(TAG, "Error fetching document", e);
                    Nama.setText("Gagal memuat data");
                    Nim.setText("Gagal memuat data");
                    Prodi.setText("Gagal memuat data");
                    Angkatan.setText("Gagal memuat data");
                    SKS.setText("Gagal memuat data");
                    IPK.setText("Gagal memuat data");
                    Yudisium.setText("Gagal memuat data");
                });
    }
}
