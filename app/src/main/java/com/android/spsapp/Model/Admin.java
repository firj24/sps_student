package com.android.spsapp.Model;

public class Admin {
    private String nama;
    private String username;
    private String password;

    public Admin() {
        // Diperlukan untuk deserialisasi Firestore
    }

    public Admin(String nama, String username, String password) {
        this.nama = nama;
        this.username = username;
        this.password = password;
    }

    // Getter dan setter
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
}
