package com.android.spsapp.Model;

public class Kaprodi {
    private String id;
    private String npm;
    private String nama;
    private String prodi;
    private String email;
    private String password;

    public Kaprodi() {
        // Empty constructor needed for Firestore
    }

    public Kaprodi(String id, String npm, String nama, String prodi, String email, String password) {
        this.id = id;
        this.npm = npm;
        this.nama = nama;
        this.prodi = prodi;
        this.email = email;
        this.password = password;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNpm() { return npm; }
    public void setNpm(String npm) { this.npm = npm; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getProdi() { return prodi; }
    public void setProdi(String prodi) { this.prodi = prodi; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
