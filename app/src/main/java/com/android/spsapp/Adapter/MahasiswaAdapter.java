package com.android.spsapp.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.spsapp.Model.Mahasiswa;
import com.android.spsapp.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.MahasiswaViewHolder> {

    private List<Mahasiswa> mahasiswaList;

    public MahasiswaAdapter(List<Mahasiswa> mahasiswaList) {
        this.mahasiswaList = mahasiswaList;
    }

    @NonNull
    @Override
    public MahasiswaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_akun_mahasiswa, parent, false);
        return new MahasiswaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MahasiswaViewHolder holder, int position) {
        Mahasiswa mahasiswa = mahasiswaList.get(position);
        holder.namaMhs.setText(mahasiswa.getNama());
        holder.userMhs.setText(mahasiswa.getUsername());
        holder.passMhs.setText(mahasiswa.getPassword());

        holder.hapusAkunMhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setTitle("Konfirmasi Hapus");
                builder.setMessage("Apakah Anda yakin ingin menghapus akun mahasiswa ini?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("DataMhs").document(mahasiswa.getId())
                                .delete()
                                .addOnSuccessListener(aVoid -> {
                                    // Cek apakah posisi yang dihapus ada di dalam daftar
                                    if (position != RecyclerView.NO_POSITION && position < mahasiswaList.size()) {
                                        mahasiswaList.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, mahasiswaList.size());
                                        Toast.makeText(holder.itemView.getContext(), "Akun mahasiswa berhasil dihapus", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(holder.itemView.getContext(), "Gagal menghapus akun mahasiswa", Toast.LENGTH_SHORT).show();
                                });
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return mahasiswaList.size();
    }

    static class MahasiswaViewHolder extends RecyclerView.ViewHolder {
        TextView namaMhs, userMhs, passMhs;
        ImageView hapusAkunMhs;

        MahasiswaViewHolder(View itemView) {
            super(itemView);
            namaMhs = itemView.findViewById(R.id.namamhs);
            userMhs = itemView.findViewById(R.id.usermhs);
            passMhs = itemView.findViewById(R.id.passmhs);
            hapusAkunMhs = itemView.findViewById(R.id.hapusakunmhs);
        }
    }
}
