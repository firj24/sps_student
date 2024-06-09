package com.android.spsapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.spsapp.Adapter.JurnalAdapter;
import com.android.spsapp.Adapter.JurnalKapAdapter;
import com.android.spsapp.Model.Jurnal;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class JurnalMhsKap extends AppCompatActivity {

    private RecyclerView recyclerView;
    private JurnalKapAdapter jurnalKapAdapter;
    private ArrayList<Jurnal> jurnalList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jurnal_mhs_kap);

        recyclerView = findViewById(R.id.recyclerjurnal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        jurnalList = new ArrayList<>();
        jurnalKapAdapter= new JurnalKapAdapter(jurnalList, this);
        recyclerView.setAdapter(jurnalKapAdapter);
        db = FirebaseFirestore.getInstance();
        fetchDataFromFirestore();
    }

    private void fetchDataFromFirestore() {
        db.collection("jurnals")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    jurnalList.clear();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Jurnal jurnal = document.toObject(Jurnal.class);
                        jurnalList.add(jurnal);
                    }
                    jurnalKapAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> Toast.makeText(JurnalMhsKap.this, "Failed to fetch jurnals", Toast.LENGTH_SHORT).show());
    }
}
