package com.android.spsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.spsapp.EditMahasiswaActivity;
import com.android.spsapp.Model.MahasiswaAdminModel;
import com.android.spsapp.R;
import java.util.List;

public class MahasiswaAdminAdapter extends RecyclerView.Adapter<MahasiswaAdminAdapter.MahasiswaViewHolder> {
    private List<MahasiswaAdminModel> mahasiswaList;
    private Context context;

    public MahasiswaAdminAdapter(Context context, List<MahasiswaAdminModel> mahasiswaList) {
        this.context = context;
        this.mahasiswaList = mahasiswaList;
    }
    private OnItemClickListener listener;
    private OnEditClickListener editListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnEditClickListener {
        void onEditClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnEditClickListener(OnEditClickListener listener) {
        this.editListener = listener;
    }

    public MahasiswaAdminAdapter(List<MahasiswaAdminModel> mahasiswaList) {
        this.mahasiswaList = mahasiswaList;
    }

    @NonNull
    @Override
    public MahasiswaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_studi_mhsadmin, parent, false);
        return new MahasiswaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MahasiswaViewHolder holder, int position) {
        MahasiswaAdminModel mahasiswa = mahasiswaList.get(position);
        holder.nama.setText(mahasiswa.getNama());
        holder.nim.setText(mahasiswa.getNim());
        holder.prodi.setText(mahasiswa.getProdi());
        holder.angkatan.setText(mahasiswa.getAngkatan());
        holder.ipk.setText(mahasiswa.getIpk());
        holder.sks.setText(mahasiswa.getSks());
        holder.yudisium.setText(mahasiswa.getYudisium());

        holder.editt.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditMahasiswaActivity.class);
            intent.putExtra("nama", mahasiswa.getNama());
            intent.putExtra("nim", mahasiswa.getNim());
            intent.putExtra("prodi", mahasiswa.getProdi());
            intent.putExtra("angkatan", mahasiswa.getAngkatan());
            intent.putExtra("ipk", mahasiswa.getIpk());
            intent.putExtra("sks", mahasiswa.getSks());
            intent.putExtra("yudisium", mahasiswa.getYudisium());
            intent.putExtra("documentId", mahasiswa.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mahasiswaList.size();
    }

    static class MahasiswaViewHolder extends RecyclerView.ViewHolder {
        TextView nim, nama, prodi, angkatan, ipk, sks, yudisium;
        ImageView editt;

        MahasiswaViewHolder(View itemView) {
            super(itemView);
            nim = itemView.findViewById(R.id.nimmhsad);
            nama = itemView.findViewById(R.id.namamhsad);
            prodi = itemView.findViewById(R.id.prodiad);
            angkatan = itemView.findViewById(R.id.angkatanad);
            ipk = itemView.findViewById(R.id.ipkad);
            sks = itemView.findViewById(R.id.sksad);
            yudisium = itemView.findViewById(R.id.yudad);
            editt = itemView.findViewById(R.id.editmhad);
        }
    }
}
