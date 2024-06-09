package com.android.spsapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.spsapp.Model.Kaprodi;
import com.android.spsapp.R;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;

public class KaprodiAdapter extends RecyclerView.Adapter<KaprodiAdapter.KaprodiViewHolder> {

    private List<Kaprodi> kaprodiList;

    public KaprodiAdapter(List<Kaprodi> kaprodiList) {
        this.kaprodiList = kaprodiList;
    }

    @NonNull
    @Override
    public KaprodiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data_kaprodi, parent, false);
        return new KaprodiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KaprodiViewHolder holder, int position) {
        Kaprodi kaprodi = kaprodiList.get(position);
        holder.npm.setText(kaprodi.getNpm());
        holder.nama.setText(kaprodi.getNama());
        holder.prodi.setText(kaprodi.getProdi());

        holder.hapus.setOnClickListener(v -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("DataKaprodi").document(kaprodi.getId())
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        kaprodiList.remove(position);
                        notifyItemRemoved(position);
                    });
        });
    }

    @Override
    public int getItemCount() {
        return kaprodiList.size();
    }

    public static class KaprodiViewHolder extends RecyclerView.ViewHolder {
        TextView npm, nama, prodi;
        ImageView hapus;

        public KaprodiViewHolder(@NonNull View itemView) {
            super(itemView);
            npm = itemView.findViewById(R.id.npmkap);
            nama = itemView.findViewById(R.id.namakap);
            prodi = itemView.findViewById(R.id.prodikap);
            hapus = itemView.findViewById(R.id.hapuskaprodi);
        }
    }
}
