package com.android.spsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.spsapp.databinding.ActivityLupaPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LupaPassword extends AppCompatActivity {
    ActivityLupaPasswordBinding binding;
    FirebaseAuth auth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityLupaPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Buat Akun Baru");
        progressDialog.setMessage("Tunggu");

        binding.lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =binding.emailreset.getText().toString();
                progressDialog.dismiss();

                if (email.isEmpty()){
                    binding.emailreset.setError("Masukkan email Anda");
                }else {
                    auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                progressDialog.dismiss();
                                Toast.makeText(LupaPassword.this, "Cek email Anda", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LupaPassword.this, Login.class));
                            }else {
                                progressDialog.dismiss();
                                Toast.makeText(LupaPassword.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }


                        }
                    });

                }

            };
        });

        binding.ingatpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LupaPassword.this, Login.class));
            }
        });
    }
}