package com.android.spsapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.spsapp.Model.AkunMahasiswaa;
import com.android.spsapp.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AkunMahasiswaAdapter extends RecyclerView.Adapter<AkunMahasiswaAdapter.AkunMahasiswaViewHolder> {
    private Context context;
    private List<AkunMahasiswaa> akunMahasiswaList;

    public AkunMahasiswaAdapter(Context context, List<AkunMahasiswaa> akunMahasiswaList) {
        this.context = context;
        this.akunMahasiswaList = akunMahasiswaList;
    }

    @NonNull
    @Override
    public AkunMahasiswaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_akun_mahasiswa, parent, false);
        return new AkunMahasiswaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AkunMahasiswaViewHolder holder, int position) {
        AkunMahasiswaa akunMahasiswa = akunMahasiswaList.get(position);
        holder.namaTextView.setText(akunMahasiswa.getNama());
        holder.usernameTextView.setText(akunMahasiswa.getUsername());
        holder.passwordTextView.setText(akunMahasiswa.getPassword());

        holder.hapusAkunImageView.setOnClickListener(v -> hapusAkunMahasiswa(akunMahasiswa.getUserId(), position));
    }

    @Override
    public int getItemCount() {
        return akunMahasiswaList.size();
    }

    private void hapusAkunMahasiswa(String userId, int position) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("AkunMhs").document(userId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Akun berhasil dihapus", Toast.LENGTH_SHORT).show();
                    akunMahasiswaList.remove(position);
                    notifyItemRemoved(position);
                })
                .addOnFailureListener(e -> Toast.makeText(context, "Gagal menghapus akun", Toast.LENGTH_SHORT).show());
    }

    static class AkunMahasiswaViewHolder extends RecyclerView.ViewHolder {
        TextView namaTextView;
        TextView usernameTextView;
        TextView passwordTextView;
        ImageView hapusAkunImageView;

        public AkunMahasiswaViewHolder(@NonNull View itemView) {
            super(itemView);
            namaTextView = itemView.findViewById(R.id.namamhs);
            usernameTextView = itemView.findViewById(R.id.usermhs);
            passwordTextView = itemView.findViewById(R.id.passmhs);
            hapusAkunImageView = itemView.findViewById(R.id.hapusakunmhs);
        }
    }
}
