package com.android.spsapp.Model;


public class AkunMahasiswaa {
    private String userId;
    private String nama;
    private String username;
    private String password;
    private String noHp;
    private String nim;
    private String angkatan;
    private String prodi;
    private String sks;
    private String ipk;
    private String yudisium;

    public AkunMahasiswaa() {
        // Kosongkan saja, atau isi dengan apa pun yang diperlukan
    }
    // Konstruktor dengan parameter
    public AkunMahasiswaa(String userId, String nama, String username, String password, String noHp, String nim, String angkatan, String prodi, String sks, String ipk, String yudisium) {
        this.userId = userId;
        this.nama = nama;
        this.username = username;
        this.password = password;
        this.noHp = noHp;
        this.nim = nim;
        this.angkatan = angkatan;
        this.prodi = prodi;
        this.sks = sks;
        this.ipk = ipk;
        this.yudisium = yudisium;
    }


    // Getter dan setter untuk semua atribut
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getSks() {
        return sks;
    }

    public void setSks(String sks) {
        this.sks = sks;
    }

    public String getIpk() {
        return ipk;
    }

    public void setIpk(String ipk) {
        this.ipk = ipk;
    }

    public String getYudisium() {
        return yudisium;
    }

    public void setYudisium(String yudisium) {
        this.yudisium = yudisium;
    }
}
