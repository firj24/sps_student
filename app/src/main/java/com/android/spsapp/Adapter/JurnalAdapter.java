package com.android.spsapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.spsapp.Model.Jurnal;
import com.android.spsapp.R;

import java.util.ArrayList;

public class JurnalAdapter extends RecyclerView.Adapter<JurnalAdapter.JurnalViewHolder> {
    private ArrayList<Jurnal> jurnalList;

    public JurnalAdapter(ArrayList<Jurnal> jurnalList) {
        this.jurnalList = jurnalList;
    }

    @NonNull
    @Override
    public JurnalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_riwayat_jurnal, parent, false);
        return new JurnalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JurnalViewHolder holder, int position) {
        Jurnal jurnal = jurnalList.get(position);
        holder.namaJurnal.setText(jurnal.getNamaJurnal());
        holder.status.setText(jurnal.getStatus());
    }

    @Override
    public int getItemCount() {
        return jurnalList.size();
    }

    public static class JurnalViewHolder extends RecyclerView.ViewHolder {
        TextView namaJurnal, status;

        public JurnalViewHolder(@NonNull View itemView) {
            super(itemView);
            namaJurnal = itemView.findViewById(R.id.namajurnal);
            status = itemView.findViewById(R.id.status);
        }
    }
}
