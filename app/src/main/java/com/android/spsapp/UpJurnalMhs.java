package com.android.spsapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.spsapp.Adapter.JurnalAdapter;
import com.android.spsapp.Model.Jurnal;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UpJurnalMhs extends AppCompatActivity {
    private static final int PICK_PDF_REQUEST = 1;
    private String nimMahasiswa;

    private TextView nama, nim, upload;
    private String loggedInUsername;
    private Button kirimJurnal;
    private RecyclerView recyRiwayatUpload;
    private Uri pdfUri;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private ArrayList<Jurnal> jurnalList;
    private JurnalAdapter jurnalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_jurnal_mhs);

        nama = findViewById(R.id.namamhsjur);
        nim = findViewById(R.id.nimmhsmhsjur);
        upload = findViewById(R.id.upload);
        kirimJurnal = findViewById(R.id.kirimjurnal);
        recyRiwayatUpload = findViewById(R.id.recyriwayatUpload);

        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        jurnalList = new ArrayList<>();
        jurnalAdapter = new JurnalAdapter(jurnalList);

        recyRiwayatUpload.setLayoutManager(new LinearLayoutManager(this));
        recyRiwayatUpload.setAdapter(jurnalAdapter);

        // Ambil username yang diteruskan dari MhsHome
        loggedInUsername = getIntent().getStringExtra("USERNAME_KEY");

        // Jika username ada, ambil informasi dari Firestore
        if (loggedInUsername != null && !loggedInUsername.isEmpty()) {
            fetchAndSetNamaDanNim();
            fetchJurnalHistory();
        }

        upload.setOnClickListener(v -> choosePdf());
        kirimJurnal.setOnClickListener(v -> uploadPdf());
    }

    private void choosePdf() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select PDF"), PICK_PDF_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            pdfUri = data.getData();
            String fileName = getFileNameFromUri(pdfUri); // Mendapatkan nama file dari Uri
            upload.setText(fileName); // Mengatur teks TextView menjadi nama file
        }
    }

    private String getFileNameFromUri(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

    private void uploadPdf() {
        if (pdfUri != null) {
            String fileId = UUID.randomUUID().toString();
            StorageReference storageRef = storage.getReference().child("jurnals/" + fileId + ".pdf");
            storageRef.putFile(pdfUri)
                    .addOnSuccessListener(taskSnapshot -> storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String downloadUrl = uri.toString();
                        saveJurnalToFirestore(fileId, downloadUrl);
                    }))
                    .addOnFailureListener(e -> Toast.makeText(UpJurnalMhs.this, "Failed to upload PDF", Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveJurnalToFirestore(String fileId, String downloadUrl) {
        Map<String, Object> jurnalData = new HashMap<>();
        jurnalData.put("id", fileId);
        jurnalData.put("namaJurnal", upload.getText().toString());
        jurnalData.put("status", "Menunggu Persetujuan");
        jurnalData.put("url", downloadUrl);
        jurnalData.put("username", loggedInUsername);
        jurnalData.put("nim", nimMahasiswa);

        db.collection("jurnals").document(fileId).set(jurnalData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(UpJurnalMhs.this, "Jurnal berhasil terkirim", Toast.LENGTH_SHORT).show();
                    fetchJurnalHistory();
                })
                .addOnFailureListener(e -> Toast.makeText(UpJurnalMhs.this, "Failed to save jurnal data", Toast.LENGTH_SHORT).show());
    }

    private void fetchAndSetNamaDanNim() {
        db.collection("AkunMhs").document(loggedInUsername)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Dokumen ditemukan, ambil nilai nama dan nim
                        String namaAkun = documentSnapshot.getString("nama");
                        String nimAkun = documentSnapshot.getString("nim");

                        // Tetapkan nilai nama dan nim ke TextView
                        nama.setText(namaAkun);
                        nim.setText(nimAkun);

                        // Simpan nim mahasiswa ke variabel
                        nimMahasiswa = nimAkun;
                    } else {
                        // Dokumen tidak ditemukan
                        nama.setText("Nama Tidak Ditemukan");
                        nim.setText("NIM Tidak Ditemukan");
                    }
                })
                .addOnFailureListener(e -> {
                    // Penanganan kesalahan
                    nama.setText("Gagal memuat nama");
                    nim.setText("Gagal memuat NIM");
                });
    }

    private void fetchJurnalHistory() {
        db.collection("jurnals")
                .whereEqualTo("username", loggedInUsername)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    jurnalList.clear();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Jurnal jurnal = new Jurnal(
                                document.getString("id"),
                                document.getString("namaJurnal"),
                                document.getString("status"),
                                document.getString("url"),
                                document.getString("username"),
                                document.getString("nim")
                        );
                        jurnalList.add(jurnal);
                    }
                    jurnalAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> Toast.makeText(UpJurnalMhs.this, "Failed to load jurnal history", Toast.LENGTH_SHORT).show());
    }
}
