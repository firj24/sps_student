package com.android.spsapp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.spsapp.Model.Jurnal;
import com.android.spsapp.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class JurnalKapAdapter extends RecyclerView.Adapter<JurnalKapAdapter.ViewHolder> {

    private ArrayList<Jurnal> jurnalList;
    private Context context;
    private JurnalKapListener listener; // Listener untuk mengirim status jurnal ke JurnalMhsKap

    public JurnalKapAdapter(ArrayList<Jurnal> jurnalList, Context context) {
        this.jurnalList = jurnalList;
        this.context = context;
    }

    public void setListener(JurnalKapListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hasil_jurnal_mahasiswa, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Jurnal jurnal = jurnalList.get(position);
        holder.nama.setText(jurnal.getUsername());
        holder.nim.setText(jurnal.getNim());
        holder.namaJurnal.setText(jurnal.getNamaJurnal());

        holder.downloadButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(jurnal.getUrl()));
            holder.itemView.getContext().startActivity(intent);
        });

        holder.actionButton.setText("Aksi"); // Mengatur teks awal tombol aksi

        holder.actionButton.setOnClickListener(v -> {
            // Tampilkan dialog konfirmasi
            showDialogConfirmation(holder.itemView.getContext(), jurnal.getId(), holder);


        });
    }

    @Override
    public int getItemCount() {
        return jurnalList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nama, nim, namaJurnal;
        public Button downloadButton, actionButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.namamhsup);
            nim = itemView.findViewById(R.id.nimmhsup);
            namaJurnal = itemView.findViewById(R.id.namajurnal);
            downloadButton = itemView.findViewById(R.id.buttondownloadjurnal);
            actionButton = itemView.findViewById(R.id.aksi);
        }
    }

    private void showDialogConfirmation(Context context, String jurnalId, ViewHolder holder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Konfirmasi")
                .setMessage("Apakah Anda yakin ingin mengubah status jurnal?")
                .setPositiveButton("Setuju", (dialog, which) -> {
                    // Ubah status jurnal menjadi "Disetujui" di Firestore
                    updateJurnalStatus(jurnalId, "Disetujui", holder);
                })
                .setNegativeButton("Tidak Setuju", (dialog, which) -> {
                    // Ubah status jurnal menjadi "Tidak Disetujui" di Firestore
                    updateJurnalStatus(jurnalId, "Tidak Disetujui", holder);
                })
                .show();
    }

    private void updateJurnalStatus(String jurnalId, String status, ViewHolder holder) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("jurnals").document(jurnalId)
                .update("status", status)
                .addOnSuccessListener(aVoid -> {
                    // Ubah status berhasil
                    Toast.makeText(context, "Status jurnal berhasil diubah", Toast.LENGTH_SHORT).show();
                    // Mengubah teks tombol aksi sesuai dengan nilai yang dipilih
                    holder.actionButton.setText(status.equals("Disetujui") ? "Setuju" : "Tidak Setuju");
                    // Mengirim status jurnal ke JurnalMhsKap
                    if (listener != null) {
                        listener.onStatusSelected(jurnalId, status);
                    }
                })
                .addOnFailureListener(e -> {
                    // Penanganan kesalahan
                    Toast.makeText(context, "Gagal mengubah status jurnal", Toast.LENGTH_SHORT).show();
                });
    }

    public void removeItem(int position) {
        jurnalList.remove(position);
        notifyItemRemoved(position);
    }

    // Interface untuk mendengarkan status jurnal yang dipilih
    public interface JurnalKapListener {
        void onStatusSelected(String jurnalId, String status);
    }
}
