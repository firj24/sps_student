package com.android.spsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.spsapp.Adapter.KaprodiAdapter;
import com.android.spsapp.Model.Kaprodi;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class DataKaprodi extends AppCompatActivity {

    private Button tambahKapButton;
    private RecyclerView recyclerView;
    private KaprodiAdapter adapter;
    private List<Kaprodi> kaprodiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_kaprodi);

        tambahKapButton = findViewById(R.id.tambahkap);
        recyclerView = findViewById(R.id.recyclerdatakaprodi);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        kaprodiList = new ArrayList<>();
        adapter = new KaprodiAdapter(kaprodiList);
        recyclerView.setAdapter(adapter);

        tambahKapButton.setOnClickListener(v -> {
            Intent intent = new Intent(DataKaprodi.this, TambahDataKaprodi.class);
            startActivity(intent);
        });

        fetchData();
    }

    private void fetchData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("DataKaprodi").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        kaprodiList.clear();
                        QuerySnapshot snapshots = task.getResult();
                        for (DocumentSnapshot doc : snapshots) {
                            Kaprodi kaprodi = doc.toObject(Kaprodi.class);
                            kaprodiList.add(kaprodi);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
