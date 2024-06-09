package com.android.spsapp.Model;

public class MahasiswaAdminModel {
    private String id; // Tambahkan variabel id

    private String nim;
    private String nama;
    private String prodi;
    private String angkatan;
    private String ipk;
    private String sks;
    private String yudisium;

    public MahasiswaAdminModel() {
        // Default constructor required for calls to DataSnapshot.getValue(MahasiswaAdminModel.class)
    }

    public MahasiswaAdminModel(String nim, String nama, String prodi, String angkatan, String ipk, String sks, String yudisium) {
        this.nim = nim;
        this.nama = nama;
        this.prodi = prodi;
        this.angkatan = angkatan;
        this.ipk = ipk;
        this.sks = sks;
        this.yudisium = yudisium;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNim() {
        return nim;
    }

    public String getNama() {
        return nama;
    }

    public String getProdi() {
        return prodi;
    }

    public String getAngkatan() {
        return angkatan;
    }

    public String getIpk() {
        return ipk;
    }

    public String getSks() {
        return sks;
    }

    public String getYudisium() {
        return yudisium;
    }
}
