package org.delcom.app.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "submission",
       uniqueConstraints = @UniqueConstraint(columnNames = {"mahasiswa_id", "tugas_id"}))
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mahasiswa_id", nullable = false)
    private User mahasiswa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tugas_id", nullable = false)
    private Tugas tugas;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String jawaban;

    @Column
    private Integer nilai;

    @Column
    private String catatan;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @Column(name = "dinilai_at")
    private LocalDateTime dinilaiAt;

    @PrePersist
    public void prePersist() {
        this.submittedAt = LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public User getMahasiswa() { return mahasiswa; }
    public void setMahasiswa(User mahasiswa) { this.mahasiswa = mahasiswa; }

    public Tugas getTugas() { return tugas; }
    public void setTugas(Tugas tugas) { this.tugas = tugas; }

    public String getJawaban() { return jawaban; }
    public void setJawaban(String jawaban) { this.jawaban = jawaban; }

    public Integer getNilai() { return nilai; }
    public void setNilai(Integer nilai) { this.nilai = nilai; }

    public String getCatatan() { return catatan; }
    public void setCatatan(String catatan) { this.catatan = catatan; }

    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }

    public LocalDateTime getDinilaiAt() { return dinilaiAt; }
    public void setDinilaiAt(LocalDateTime dinilaiAt) { this.dinilaiAt = dinilaiAt; }
}
