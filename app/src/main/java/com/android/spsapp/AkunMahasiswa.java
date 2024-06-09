package com.android.spsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.spsapp.Adapter.AkunMahasiswaAdapter;
import com.android.spsapp.Model.AkunMahasiswaa;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AkunMahasiswa extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AkunMahasiswaAdapter adapter;
    private List<AkunMahasiswaa> akunMahasiswaList;
    private FirebaseFirestore db;
    private Button tambahakun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun_mahasiswa);

        tambahakun = findViewById(R.id.tambahmhs);
        tambahakun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AkunMahasiswa.this, TambahAKunMhs.class);
                startActivity(intent);
                finish();
            }
        });

        recyclerView = findViewById(R.id.recyclerakunmhs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        akunMahasiswaList = new ArrayList<>();
        adapter = new AkunMahasiswaAdapter(this, akunMahasiswaList);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        db.collection("AkunMhs")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            return;
                        }
                        akunMahasiswaList.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            AkunMahasiswaa akunMahasiswa = doc.toObject(AkunMahasiswaa.class);
                            akunMahasiswaList.add(akunMahasiswa);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
