package org.delcom.app.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tugas")
public class Tugas {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String judul;

    @Column(columnDefinition = "TEXT")
    private String deskripsi;

    @Column(name = "deadline", nullable = false)
    private LocalDateTime deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mata_kuliah_id", nullable = false)
    private MataKuliah mataKuliah;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getJudul() { return judul; }
    public void setJudul(String judul) { this.judul = judul; }

    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }

    public LocalDateTime getDeadline() { return deadline; }
    public void setDeadline(LocalDateTime deadline) { this.deadline = deadline; }

    public MataKuliah getMataKuliah() { return mataKuliah; }
    public void setMataKuliah(MataKuliah mataKuliah) { this.mataKuliah = mataKuliah; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
