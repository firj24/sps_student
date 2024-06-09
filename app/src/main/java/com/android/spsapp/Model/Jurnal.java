package com.android.spsapp.Model;

public class Jurnal {
    private String id;
    private String namaJurnal;
    private String status;
    private String url;
    private String username; // Untuk menyimpan username pengguna
    private String nim; // Untuk menyimpan NIM mahasiswa

    public Jurnal(String id, String namaJurnal, String status, String url, String username, String nim) {
        this.id = id;
        this.namaJurnal = namaJurnal;
        this.status = status;
        this.url = url;
        this.username = username;
        this.nim= nim;
    }

    public  Jurnal(){}
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamaJurnal() {
        return namaJurnal;
    }

    public void setNamaJurnal(String namaJurnal) {
        this.namaJurnal = namaJurnal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }
}
