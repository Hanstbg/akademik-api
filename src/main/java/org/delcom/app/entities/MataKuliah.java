package org.delcom.app.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "mata_kuliah")
public class MataKuliah {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nama;

    @Column(nullable = false, unique = true)
    private String kode;

    @Column
    private String deskripsi;

    @Column(nullable = false)
    private int sks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dosen_id", nullable = false)
    private User dosen;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getKode() { return kode; }
    public void setKode(String kode) { this.kode = kode; }

    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }

    public int getSks() { return sks; }
    public void setSks(int sks) { this.sks = sks; }

    public User getDosen() { return dosen; }
    public void setDosen(User dosen) { this.dosen = dosen; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
