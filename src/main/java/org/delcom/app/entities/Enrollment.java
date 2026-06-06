package org.delcom.app.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "enrollment",
       uniqueConstraints = @UniqueConstraint(columnNames = {"mahasiswa_id", "mata_kuliah_id"}))
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mahasiswa_id", nullable = false)
    private User mahasiswa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mata_kuliah_id", nullable = false)
    private MataKuliah mataKuliah;

    @Column(name = "enrolled_at")
    private LocalDateTime enrolledAt;

    @PrePersist
    public void prePersist() {
        this.enrolledAt = LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public User getMahasiswa() { return mahasiswa; }
    public void setMahasiswa(User mahasiswa) { this.mahasiswa = mahasiswa; }

    public MataKuliah getMataKuliah() { return mataKuliah; }
    public void setMataKuliah(MataKuliah mataKuliah) { this.mataKuliah = mataKuliah; }

    public LocalDateTime getEnrolledAt() { return enrolledAt; }
    public void setEnrolledAt(LocalDateTime enrolledAt) { this.enrolledAt = enrolledAt; }
}
