package org.delcom.app.repositories;

import org.delcom.app.entities.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    List<Enrollment> findAllByMahasiswaId(UUID mahasiswaId);
    boolean existsByMahasiswaIdAndMataKuliahId(UUID mahasiswaId, UUID mataKuliahId);
    Optional<Enrollment> findByMahasiswaIdAndMataKuliahId(UUID mahasiswaId, UUID mataKuliahId);
}
