package com.android.spsapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.spsapp.Adapter.AkunMhsAdapter;
import com.android.spsapp.Model.AkunMhs;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MasaStudiMahasiswaAdmin extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AkunMhsAdapter adapter;
    private List<AkunMhs> akunMhsList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masa_studi_mahasiswa_admin);
        db = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.recyclermhsAdmin);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        akunMhsList = new ArrayList<>();
        adapter = new AkunMhsAdapter(this, akunMhsList);
        recyclerView.setAdapter(adapter);

        loadDataFromFirestore();
    }

    private void loadDataFromFirestore() {
        db.collection("AkunMhs").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        akunMhsList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            AkunMhs akunMhs = document.toObject(AkunMhs.class);
                            akunMhsList.add(akunMhs);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
