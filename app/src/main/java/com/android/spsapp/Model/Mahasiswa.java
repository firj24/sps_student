package com.android.spsapp.Model;

public class Mahasiswa {
    private String id;
    private String nama;
    private String username;
    private String password;
    private String noHp;
    private String nim;
    private String angkatan;
    private String prodi;
    private String ipk;
    private String sks;
    private String yudisium;

    public Mahasiswa() {
        // Required empty constructor for Firestore serialization
    }

    public Mahasiswa(String nama, String username, String password, String noHp, String nim, String angkatan, String prodi, String ipk, String sks, String yudisium) {
        this.nama = nama;
        this.username = username;
        this.password = password;
        this.noHp = noHp;
        this.nim = nim;
        this.angkatan = angkatan;
        this.prodi = prodi;
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

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getAngkatan() {
        return angkatan;
    }

    public void setAngkatan(String angkatan) {
        this.angkatan = angkatan;
    }

    public String getProdi() {
        return prodi;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }

    public String getIpk() {
        return ipk;
    }

    public void setIpk(String ipk) {
        this.ipk = ipk;
    }

    public String getSks() {
        return sks;
    }

    public void setSks(String sks) {
        this.sks = sks;
    }

    public String getYudisium() {
        return yudisium;
    }

    public void setYudisium(String yudisium) {
        this.yudisium = yudisium;
    }
}
