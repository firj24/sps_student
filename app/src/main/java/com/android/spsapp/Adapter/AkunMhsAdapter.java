package com.android.spsapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.spsapp.Model.AkunMhs;
import com.android.spsapp.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;

public class AkunMhsAdapter extends RecyclerView.Adapter<AkunMhsAdapter.ViewHolder> {
    private Context context;
    private List<AkunMhs> akunMhsList;
    private FirebaseFirestore db;

    public AkunMhsAdapter(Context context, List<AkunMhs> akunMhsList) {
        this.context = context;
        this.akunMhsList = akunMhsList;
        this.db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_studi_mhsadmin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AkunMhs akunMhs = akunMhsList.get(position);
        holder.bind(akunMhs);
    }

    @Override
    public int getItemCount() {
        return akunMhsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView namaTextView, nimTextView, prodiTextView, angkatanTextView, ipkTextView, sksTextView, yudisiumTextView;
        ImageView editImageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaTextView = itemView.findViewById(R.id.namamhsad);
            nimTextView = itemView.findViewById(R.id.nimmhsad);
            prodiTextView = itemView.findViewById(R.id.prodiad);
            angkatanTextView = itemView.findViewById(R.id.angkatanad);
            ipkTextView = itemView.findViewById(R.id.ipkad);
            sksTextView = itemView.findViewById(R.id.sksad);
            yudisiumTextView = itemView.findViewById(R.id.yudad);
            editImageView = itemView.findViewById(R.id.editmhad);

            editImageView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    AkunMhs akunMhs = akunMhsList.get(position);
                    showEditDialog(akunMhs);
                }
            });
        }

        void bind(AkunMhs akunMhs) {
            namaTextView.setText(akunMhs.getNama());
            nimTextView.setText(akunMhs.getNim());
            prodiTextView.setText(akunMhs.getProdi());
            angkatanTextView.setText(akunMhs.getAngkatan());
            ipkTextView.setText(akunMhs.getIpk());
            sksTextView.setText(akunMhs.getSks());
            yudisiumTextView.setText(akunMhs.getYudisium());
        }

        private void showEditDialog(AkunMhs akunMhs) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.dialog_edit_mahasiswa, null);
            builder.setView(view);

            EditText editNama = view.findViewById(R.id.editNama);
            EditText editNim = view.findViewById(R.id.editNim);
            EditText editProdi = view.findViewById(R.id.editProdi);
            EditText editAngkatan = view.findViewById(R.id.editAngkatan);
            EditText editIpk = view.findViewById(R.id.editIpk);
            EditText editSks = view.findViewById(R.id.editSks);
            EditText editYudisium = view.findViewById(R.id.editYudisium);
            Button buttonSave = view.findViewById(R.id.buttonSave);

            editNama.setText(akunMhs.getNama());
            editNim.setText(akunMhs.getNim());
            editProdi.setText(akunMhs.getProdi());
            editAngkatan.setText(akunMhs.getAngkatan());
            editIpk.setText(akunMhs.getIpk());
            editSks.setText(akunMhs.getSks());
            editYudisium.setText(akunMhs.getYudisium());

            AlertDialog dialog = builder.create();

            buttonSave.setOnClickListener(v -> {
                String nama = editNama.getText().toString();
                String nim = editNim.getText().toString();
                String prodi = editProdi.getText().toString();
                String angkatan = editAngkatan.getText().toString();
                String ipk = editIpk.getText().toString();
                String sks = editSks.getText().toString();
                String yudisium = editYudisium.getText().toString();

                if (nama.isEmpty() || nim.isEmpty() || prodi.isEmpty() ||
                        angkatan.isEmpty() || ipk.isEmpty() || sks.isEmpty() || yudisium.isEmpty()) {
                    Toast.makeText(context, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
                    return;
                }

                updateMahasiswa(akunMhs.getUserId(), nama, nim, prodi, angkatan, ipk, sks, yudisium, dialog);
            });

            dialog.show();
        }

        private void updateMahasiswa(String documentId, String nama, String nim, String prodi, String angkatan, String ipk, String sks, String yudisium, AlertDialog dialog) {
            DocumentReference documentReference = db.collection("AkunMhs").document(documentId);
            documentReference.update("nama", nama, "nim", nim, "prodi", prodi, "angkatan", angkatan, "ipk", ipk, "sks", sks, "yudisium", yudisium)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(context, "Data mahasiswa berhasil diperbarui", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    })
                    .addOnFailureListener(e -> Toast.makeText(context, "Gagal memperbarui data mahasiswa: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }
}
